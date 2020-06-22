package com.ethan.ryds.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ryds.entity.sys.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-18
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 添加或更新用户
     */
    void saveOrUpdate(Long userId, List<Long> roleIdList);

}
