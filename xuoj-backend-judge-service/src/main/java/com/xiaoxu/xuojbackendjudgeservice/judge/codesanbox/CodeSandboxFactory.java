package com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox;


import com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.impl.ExampleCodeSandbox;
import com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.impl.RemoteCodeSandbox;
import com.xiaoxu.xuojbackendjudgeservice.judge.codesanbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱创建工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {
    /**
     * 创建代码沙箱实例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
