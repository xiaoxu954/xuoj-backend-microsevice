package com.xiaoxu.xuojbackendmodel.model.codesandbox;

import lombok.Data;

/**
 * 题目判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存（KB）
     */
    private Integer memory;

    /**
     * 消耗时间（KB）
     */
    private Integer time;
}
