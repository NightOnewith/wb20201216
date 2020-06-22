package com.ethan.ryds.dao.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ethan.ryds.entity.sys.SysUserRole;

import java.util.List;

/**
 * <p>
 * 用户与角色对应关系 Mapper 接口
 * </p>
 *
 * @author Ethan
 * @since 2020-06-18
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

}
