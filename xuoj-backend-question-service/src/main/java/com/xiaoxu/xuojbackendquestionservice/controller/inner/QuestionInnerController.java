package com.xiaoxu.xuojbackendquestionservice.controller.inner;


import com.xiaoxu.xuojbackendmodel.model.entity.Question;
import com.xiaoxu.xuojbackendmodel.model.entity.QuestionSubmit;
import com.xiaoxu.xuojbackendquestionservice.service.QuestionService;
import com.xiaoxu.xuojbackendquestionservice.service.QuestionSubmitService;
import com.xiaoxu.xuojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 该服务仅内部调用
 */
@RestController()
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Override
    @GetMapping("/get/id")
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @Override
    @GetMapping("/question_submitId/get/id")
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @Override
    @PostMapping("/question_submitId/update")
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

    @Override
    public boolean updateQuestion(Question question) {
        return questionService.updateById(question);
    }
}