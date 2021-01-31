package com.ethan.ryds.entity.module;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 测试题表
 * </p>
 *
 * @author Ethan
 * @since 2020-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuestionTest extends Model<QuestionTest> {

    private static final long serialVersionUID=1L;

    /**
     * 序号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 问题内容
     */
    private String questionContent;

    /**
     * 正确答案
     */
    private Integer answer;

    /**
     * 选项A
     */
    @TableField("optionA")
    private String optionA;

    /**
     * 选项B
     */
    @TableField("optionB")
    private String optionB;

    /**
     * 选项C
     */
    @TableField("optionC")
    private String optionC;

    /**
     * 选项D
     */
    @TableField("optionD")
    private String optionD;

    /**
     * 选项集合
     */
    @TableField(exist = false)
    private List<String> optionList;

    /**
     * 问题类型： 1： 单选
     */
    private Integer type;

    /**
     * 问题分数
     */
    private Integer score;

    /**
     * 答案分析
     */
    private String analysis;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
