package com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.impl;


import com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.CodeSandbox;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.JudgeInfo;
import com.xiaoxu.xuojbackendmodel.model.enums.JudgeInfoMessageEnum;
import com.xiaoxu.xuojbackendmodel.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getText());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100);
        judgeInfo.setTime(100);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
