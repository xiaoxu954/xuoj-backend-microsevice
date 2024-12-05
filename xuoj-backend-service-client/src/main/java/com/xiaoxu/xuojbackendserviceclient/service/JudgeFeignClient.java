package com.xiaoxu.xuojbackendserviceclient.service;


import com.xiaoxu.xuojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 判题服务
 */
@FeignClient(name = "xuoj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {

    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
