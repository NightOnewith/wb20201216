package com.ethan.ryds.dto;

import lombok.Data;

/**
 * @Description 学生修改密码表单
 * @Author Ethan
 * @Date 2020/7/15 13:39
 */
@Data
public class StuUpdatePwdForm {

    private Long userId;

    private String oldPassword;

    private String newPassword;
}
