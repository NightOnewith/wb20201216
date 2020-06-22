package com.ethan.ryds.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ethan.ryds.entity.sys.SysUser;

import java.util.List;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author Ethan
 * @since 2020-03-31
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 获取用户角色
     */
    List<String> getUserRole(long userId);

}
