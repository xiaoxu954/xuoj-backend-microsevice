package com.xiaoxu.xuojbackendserviceclient.service;


import com.xiaoxu.xuojbackendmodel.model.entity.Question;
import com.xiaoxu.xuojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 题目服务
 */
@FeignClient(name = "xuoj-backend-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {


    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submitId/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionSubmitId") long questionSubmitId);

    @PostMapping("/question_submitId/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

    @PostMapping("/question/update")
    boolean updateQuestion(@RequestBody Question question);


}
