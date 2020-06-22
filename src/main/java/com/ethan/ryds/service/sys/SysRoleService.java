package com.ethan.ryds.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ryds.entity.sys.SysRole;
import com.ethan.ryds.common.utils.PageUtils;

import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-18
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRole role);

    void update(SysRole role);

    void deleteBatch(Long roleId);

}
