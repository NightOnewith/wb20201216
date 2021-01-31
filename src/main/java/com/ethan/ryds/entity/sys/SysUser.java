package com.ethan.ryds.entity.sys;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author Ethan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID=1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @Excel(name = "用户名", orderNum = "1")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", orderNum = "2")
    private String email;

    /**
     * 手机号
     */
    @Excel(name = "手机号", orderNum = "3")
    private Long mobile;

    /**
     * 学校
     */
    @Excel(name = "学校", orderNum = "4")
    private String school;

    /**
     * 班级
     */
    @Excel(name = "班级", orderNum = "5")
    private String classmate;

    /**
     * 专业
     */
    @Excel(name = "专业", orderNum = "6")
    private String specialty;

    /**
     * 职务  0：管理员   1：教师   2：学生
     */
    private Integer position;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    /**
     * 创建者ID
     */
    @TableField(value = "create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 角色ID列表
     */
    @TableField(exist=false)
    private List<Long> roleIdList;

    /**
     * 教师拥有的班级列表
     */
    @TableField(exist=false)
    private List<String> classmateList;


    @Override
    protected Serializable pkVal() {
        return this.userId;
    }

}
