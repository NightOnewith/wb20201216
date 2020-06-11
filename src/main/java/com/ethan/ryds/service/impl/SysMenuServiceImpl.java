package com.ethan.ryds.service.impl;

import com.ethan.ryds.constant.Constants;
import com.ethan.ryds.dto.SysMenuDto;
import com.ethan.ryds.entity.SysMenu;
import com.ethan.ryds.dao.SysMenuMapper;
import com.ethan.ryds.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单管理 服务实现类
 * </p>
 *
 * @author Ethan
 * @since 2020-06-04
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public List<SysMenu> queryListParentId(Long parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }

        List<SysMenu> userMenuList = new ArrayList<>();
        for(SysMenu menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuDto> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == Constants.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuDto> getAllMenuList(List<Long> menuIdList){
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L, menuIdList);

        List<SysMenuDto> menuDtoList = replaceMenuDto(menuList);
        /*for (SysMenuDto sysMenu : menuDtoList) {
            sysMenu.setAlwaysShow(true);
        }*/

        //递归获取子菜单
        List<SysMenuDto> menuTreeList = getMenuTreeList(menuDtoList, menuIdList);

        return menuTreeList;
    }

    /**
     * 递归
     */
    private List<SysMenuDto> getMenuTreeList(List<SysMenuDto> menuList, List<Long> menuIdList){
        List<SysMenuDto> subMenuList = new ArrayList<>();

        for(SysMenuDto entity : menuList){
            //目录
            if(entity.getType() == Constants.MenuType.CATALOG.getValue()){
                List<SysMenu> childrenList = queryListParentId(entity.getId(), menuIdList);
                List<SysMenuDto> childrenListDto = replaceMenuDto(childrenList);
                List<SysMenuDto> treeList = getMenuTreeList(childrenListDto, menuIdList);
                entity.setChildren(treeList);
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    private List<SysMenuDto> replaceMenuDto(List<SysMenu> menuList) {
        List<SysMenuDto> menuListDto = new ArrayList<>();

        for (SysMenu sysmenu : menuList) {
            SysMenuDto sysMenuDto = new SysMenuDto();
            sysMenuDto.setId(sysmenu.getMenuId());
            sysMenuDto.setName(sysmenu.getName());

            if(0 == sysmenu.getParentId()) {
                sysMenuDto.setComponent("Layout");
            } else {
                sysMenuDto.setComponent(sysmenu.getUrl());
            }

            sysMenuDto.setType(sysmenu.getType());
            sysMenuDto.setPath(sysmenu.getUrl());

            SysMenuDto.Meta meta = new SysMenuDto.Meta();
            meta.setTitle(sysmenu.getName());
            meta.setIcon(sysmenu.getIcon());

            sysMenuDto.setMeta(meta);

            menuListDto.add(sysMenuDto);
        }

        return menuListDto;
    }
}
