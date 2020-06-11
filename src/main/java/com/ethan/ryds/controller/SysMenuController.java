package com.ethan.ryds.controller;

import com.ethan.ryds.constant.Constants;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dto.SysMenuDto;
import com.ethan.ryds.entity.SysMenu;
import com.ethan.ryds.service.SysMenuService;
import com.ethan.ryds.service.SysUserService;
import com.ethan.ryds.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单管理 前端控制器
 * </p>
 *
 * @author Ethan
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav(){
        List<SysMenuDto> menuList = sysMenuService.getUserMenuList(getCurrUserId());
        Set<String> permissions = sysUserService.getUserPermissions(getCurrUserId());
        Set<String> userRole = sysUserService.getUserRole(getCurrUserId());
        String username = getCurrUser().getUsername();

        System.out.println(R.ok().put("routers", menuList).put("name", username).put("roles", userRole));

        return R.ok().put("routers", menuList).put("name", username).put("roles", userRole); // .put("permissions", permissions)
    }

}

