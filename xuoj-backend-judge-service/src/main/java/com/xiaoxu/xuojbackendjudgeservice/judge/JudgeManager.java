package com.xiaoxu.xuojbackendjudgeservice.judge;


import com.xiaoxu.xuojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.xiaoxu.xuojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.xiaoxu.xuojbackendjudgeservice.judge.strategy.JudgeContext;
import com.xiaoxu.xuojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.xiaoxu.xuojbackendmodel.model.codesandbox.JudgeInfo;
import com.xiaoxu.xuojbackendmodel.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {
    /**
     * 执行判题接口
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();

        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
