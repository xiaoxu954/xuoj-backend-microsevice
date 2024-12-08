package com.xiaoxu.xuojbackendmodel.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName(value = "question_comment")
public class QuestionComment implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 题目id
     */
    private Long questionId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 评论内容
     */

    private String content;

    /**
     * 父级评论id
     */
    private Long parentId;
    /**
     * 回复条数
     */
    private Integer commentNum;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 是否点赞
     */
    private Boolean isLike;

    /**
     * 点赞id列表
     */
    private String likeListId;

    /**
     * 是否显示输入框
     */
    private Boolean inputShow;

    /**
     * 被回复的记录id
     */
    private Long fromId;

    /**
     * 回复人名称
     */
    private String fromName;

    /**
     * 更新时间
     */
    private Date gmtModified;
    /**
     * 创建时间
     */
    private Date gmtCreate;


    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    @TableLogic
    private Boolean isDelete;


}
