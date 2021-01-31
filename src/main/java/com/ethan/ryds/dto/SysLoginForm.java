package com.ethan.ryds.dto;

import lombok.Data;

/**
 * 登录表单
 * Created by ASUS on 2020/6/1.
 */
@Data
public class SysLoginForm {

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录平台
     */
    private String platform;
}
