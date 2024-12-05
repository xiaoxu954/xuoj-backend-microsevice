package com.xiaoxu.xuojbackendmodel.model.dto.questionsubmit;


import com.xiaoxu.xuojbackendcommon.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {


    /**
     * 编程语言
     */
    private String language;


    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 公开状态
     */
    private Integer visible;

    private static final long serialVersionUID = 1L;
}