package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讨论主题下的评论回复
 * </p>
 *
 * @author Ethan
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentReply extends Model<CommentReply> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 主题id
     */
    private Integer ownerId;

    /**
     * 评论者id
     */
    private Long fromId;

    /**
     * 评论者姓名
     */
    private String fromName;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 父评论id，即该表的id
     */
    private Integer commentId;

    /**
     * 被评论者id
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long toId;

    /**
     * 被评论者姓名
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String toName;

    /**
     * 评论时间
     */
    private LocalDateTime date;

    /**
     * 评论回复集合
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private List<CommentReply> reply;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
