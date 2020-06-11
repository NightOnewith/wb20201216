package com.ethan.ryds.service;

import com.ethan.ryds.dto.SysMenuDto;
import com.ethan.ryds.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单管理 服务类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-04
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取用户菜单列表
     */
    List<SysMenuDto> getUserMenuList(Long userId);

}
