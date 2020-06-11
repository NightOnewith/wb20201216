package com.ethan.ryds.controller.base;

import com.ethan.ryds.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * Created by ASUS on 2020/5/30.
 */
public abstract class AbstractController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    // 获取当前用户
    protected Subject getCurrUserSubject() {
        return SecurityUtils.getSubject();
    }

    // 获取当前用户对象
    protected SysUser getCurrUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    // 获取当前用户对象ID
    protected Long getCurrUserId() {
        return getCurrUser().getUserId();
    }
}
