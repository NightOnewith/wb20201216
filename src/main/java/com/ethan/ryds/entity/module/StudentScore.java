package com.ethan.ryds.entity.module;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生实验成绩表
 * </p>
 *
 * @author Ethan
 * @since 2020-07-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StudentScore extends Model<StudentScore> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @Excel(name = "编号", orderNum = "1")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学生姓名
     */
    @Excel(name = "学生姓名", orderNum = "2")
    private String account;

    /**
     * 实验成绩
     */
    @Excel(name = "实验成绩", orderNum = "3")
    private Integer score;

    /**
     * 测试总分
     */
    @Excel(name = "实验总分", orderNum = "4")
    private Integer totalScore;

    /**
     * 实验名称
     */
    @Excel(name = "实验名称", orderNum = "5")
    private String projectName;

    /**
     * 提交时间
     */
    @Excel(name = "提交时间", orderNum = "6")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
