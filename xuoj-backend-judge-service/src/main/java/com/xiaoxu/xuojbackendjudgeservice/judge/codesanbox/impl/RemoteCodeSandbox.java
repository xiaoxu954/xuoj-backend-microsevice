package com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.xiaoxu.xuojbackendcommon.exception.BusinessException;
import com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.CodeSandbox;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.ExecuteCodeResponse;

import static com.xiaoxu.xuojbackendcommon.common.ErrorCode.API_REQUEST_ERROR;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        //todo 请求到代码沙箱的地址
//        String url = "http://localhost:8090/run";
        String url = "http://localhost:8123/executeCode";
        //192.168.206.134为本地虚拟机
//        String url = "http://192.168.206.134:8090/run";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();

        if (StrUtil.isBlank(responseStr)) {
            throw new BusinessException(API_REQUEST_ERROR, "executeCode remoteSandbox error,message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
