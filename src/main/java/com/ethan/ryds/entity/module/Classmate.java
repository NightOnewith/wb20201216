package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 班级信息
 * </p>
 *
 * @author Ethan
 * @since 2020-06-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Classmate extends Model<Classmate> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    /**
     * 班级名称
     */
    private String classname;

    /**
     * 教师id
     */
    private Long teacherId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 老师姓名
     */
    @TableField(exist=false)
    private String teacher;


    @Override
    protected Serializable pkVal() {
        return this.classId;
    }

}
