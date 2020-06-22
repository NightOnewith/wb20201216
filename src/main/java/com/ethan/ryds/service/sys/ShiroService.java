package com.ethan.ryds.service.sys;

import java.util.Set;

/**
 * @Description shiro相关接口
 * @Author Ethan
 * @Date 2020/6/17 16:04
 */
public interface ShiroService {

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);
}
