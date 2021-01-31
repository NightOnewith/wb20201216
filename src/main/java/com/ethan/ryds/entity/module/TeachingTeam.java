package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 教学团队表
 * </p>
 *
 * @author Ethan
 * @since 2020-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TeachingTeam extends Model<TeachingTeam> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 教师简介
     */
    private String introduction;

    /**
     * 描述内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
