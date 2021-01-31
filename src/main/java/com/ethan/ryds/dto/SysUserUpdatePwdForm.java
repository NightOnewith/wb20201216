package com.ethan.ryds.dto;

import lombok.Data;

/**
 * @Description 用户密码更新表单
 * @Author Ethan
 * @Date 2020/6/12 13:32
 */
@Data
public class SysUserUpdatePwdForm {

    private long userId;

    private String username;

    private String password;
}
