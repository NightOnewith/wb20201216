package com.ethan.ryds.service.sys;

import com.ethan.ryds.dto.SysMenuDto;
import com.ethan.ryds.entity.sys.SysMenu;
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
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取用户菜单列表
     */
    List<SysMenuDto> getUserMenuList(Long userId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 删除
     */
    void delete(Long menuId);

}
