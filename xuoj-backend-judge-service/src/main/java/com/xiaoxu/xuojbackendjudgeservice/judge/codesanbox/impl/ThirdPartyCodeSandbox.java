package com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.impl;

import com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.CodeSandbox;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
