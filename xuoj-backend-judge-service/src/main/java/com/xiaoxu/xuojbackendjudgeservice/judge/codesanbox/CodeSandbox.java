package com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox;


import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeResponse;

public interface CodeSandbox {
    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
