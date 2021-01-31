package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讨论区主题表
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CommentTopic extends Model<CommentTopic> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 讨论主题标题
     */
    private String title;

    /**
     * 讨论z主题内容
     */
    private String content;

    /**
     * 讨论主题发布者
     */
    private String account;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 主题评论数
     */
    @TableField(exist = false)
    private Integer replyCount;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
