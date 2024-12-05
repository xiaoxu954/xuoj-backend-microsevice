package com.xiaoxu.xuojbackendquestionservice.service.impl;


import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoxu.xuojbackendcommon.common.ErrorCode;
import com.xiaoxu.xuojbackendcommon.constant.CommonConstant;
import com.xiaoxu.xuojbackendcommon.exception.BusinessException;
import com.xiaoxu.xuojbackendcommon.utils.SqlUtils;
import com.xiaoxu.xuojbackendmodel.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.xiaoxu.xuojbackendmodel.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.xiaoxu.xuojbackendmodel.model.entity.Question;
import com.xiaoxu.xuojbackendmodel.model.entity.QuestionSubmit;
import com.xiaoxu.xuojbackendmodel.model.entity.User;
import com.xiaoxu.xuojbackendmodel.model.enums.QuestionSubmitLanguageEnum;
import com.xiaoxu.xuojbackendmodel.model.enums.QuestionSubmitStatusEnum;
import com.xiaoxu.xuojbackendmodel.model.enums.QuestionSubmitVisibleEnum;
import com.xiaoxu.xuojbackendmodel.model.vo.QuestionSubmitVO;
import com.xiaoxu.xuojbackendmodel.model.vo.QuestionVO;
import com.xiaoxu.xuojbackendquestionservice.mapper.QuestionSubmitMapper;
import com.xiaoxu.xuojbackendquestionservice.rabbitmq.MyMessageProducer;
import com.xiaoxu.xuojbackendquestionservice.service.QuestionService;
import com.xiaoxu.xuojbackendquestionservice.service.QuestionSubmitService;
import com.xiaoxu.xuojbackendserviceclient.service.JudgeFeignClient;
import com.xiaoxu.xuojbackendserviceclient.service.UserFeignClient;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xujihong
 * @description 针对表【question_submit(提交题目)】的数据库操作Service实现
 * @createDate 2024-08-09 16:48:07
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    @Lazy
    private JudgeFeignClient judgeService;

    @Resource
    private MyMessageProducer myMessageProducer;


    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        //todo 检验编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        Long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(questionSubmitAddRequest.getLanguage());

        //设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");

        }
        Long questionSubmitId = questionSubmit.getId();


        // 发送消息
        myMessageProducer.sendMessage("code_exchange", "my_routingKey", String.valueOf(questionSubmitId));
        //执行判题服务
//
//        CompletableFuture.runAsync(() -> {
//            judgeService.doJudge(questionSubmitId);
//
//        });

        return questionSubmitId;
    }

    /**
     * 获取查询包装类（用户可能根据哪些字段查询，根据前端传来的请求对象，得到mybatis框架支持的查询QueryWrapper类）
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {
        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }

        String language = questionSubmitQueryRequest.getLanguage();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long userId = questionSubmitQueryRequest.getUserId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        Integer visible = questionSubmitQueryRequest.getVisible();
        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(QuestionSubmitVisibleEnum.getEnumByValue(visible) != null, "visible", visible);

        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }


    @Override
    public QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser) {

        QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己（提交 userId 和登录用户 id 不同）提交的代码
        long userId = loginUser.getId();
        // 处理脱敏
        if (userId != questionSubmit.getUserId() && !userFeignClient.isAdmin(loginUser)) {
            questionSubmitVO.setCode(null);
        }
        Question question = questionService.getById(questionSubmit.getQuestionId());
        questionSubmitVO.setQuestionVO(QuestionVO.objToVo(question));
        return questionSubmitVO;


    }

    @Override
    public Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVO> questionSubmitVOPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollUtil.isEmpty(questionSubmitList)) {
            return questionSubmitVOPage;
        }

        List<QuestionSubmitVO> questionSubmitVOList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVO(questionSubmit, loginUser))
                .collect(Collectors.toList());
        questionSubmitVOPage.setRecords(questionSubmitVOList);
        return questionSubmitVOPage;
    }


}




