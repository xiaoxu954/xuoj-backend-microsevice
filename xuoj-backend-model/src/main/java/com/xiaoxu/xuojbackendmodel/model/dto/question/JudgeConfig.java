package com.xiaoxu.xuojbackendmodel.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Integer timeLimit;

    /**
     * 内存限制（KB）
     */
    private Integer memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Integer stackLimit;
}
