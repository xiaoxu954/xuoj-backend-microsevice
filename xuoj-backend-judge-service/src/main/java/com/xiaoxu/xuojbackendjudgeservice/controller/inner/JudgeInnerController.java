package com.xiaoxu.xuojbackendjudgeservice.controller.inner;

import com.xiaoxu.xuojbackendjudgeservice.judge.JudgeService;
import com.xiaoxu.xuojbackendmodel.model.entity.QuestionSubmit;
import com.xiaoxu.xuojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 该服务仅内部调用
 */
@RestController()
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;


    /**
     * 判题
     *
     * @param questionSubmitId
     * @return
     */
    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(long questionSubmitId) {
        return judgeService.doJudge(questionSubmitId);
    }
}