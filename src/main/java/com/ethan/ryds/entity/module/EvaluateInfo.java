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
 * 评价信息表
 * </p>
 *
 * @author Ethan
 * @since 2020-07-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class EvaluateInfo extends Model<EvaluateInfo> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评价者
     */
    private String account;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价分数
     */
    private Integer score;

    /**
     * 评价时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
