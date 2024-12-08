package com.xiaoxu.xuojbackendmodel.model.dto.questioncomment;


import com.xiaoxu.xuojbackendmodel.model.entity.QuestionComment;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class CommentAddRequest implements Serializable {
    /**
     * 父级评论
     */
    private QuestionComment parentComment;

    /**
     * 当前评论
     */
    private QuestionComment currentComment;
}
