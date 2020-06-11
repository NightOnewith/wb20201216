package com.ethan.ryds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ryds.entity.SysUser;

import java.util.List;
import java.util.Set;

/**
 * Created by ASUS on 2020/5/30.
 */
public interface SysUserService extends IService<SysUser> {

    boolean saveUser(SysUser sysUser);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 获取用户角色
     */
    Set<String> getUserRole(long userId);

}
