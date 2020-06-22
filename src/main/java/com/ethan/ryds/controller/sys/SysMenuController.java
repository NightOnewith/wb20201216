package com.ethan.ryds.controller.sys;

import com.ethan.ryds.common.log.annotation.RSysLog;
import com.ethan.ryds.common.constant.Constants;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dto.SysMenuDto;
import com.ethan.ryds.entity.sys.SysMenu;
import com.ethan.ryds.common.exception.MyException;
import com.ethan.ryds.service.sys.ShiroService;
import com.ethan.ryds.service.sys.SysMenuService;
import com.ethan.ryds.service.sys.SysUserService;
import com.ethan.ryds.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private ShiroService shiroService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav(){
        List<SysMenuDto> menuList = sysMenuService.getUserMenuList(getCurrUserId());
        Set<String> permissions = shiroService.getUserPermissions(getCurrUserId());
        Set<String> userRole = sysUserService.getUserRole(getCurrUserId());
        String username = getCurrUser().getUsername();
        String introduction = getCurrUser().getIntroduction();

        return R.ok().put("routers", menuList).put("username", username).put("roles", userRole).put("permissions", permissions).put("introduction", introduction);
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public R list(){
        List<SysMenu> menuList = sysMenuService.list();
        for(SysMenu sysMenu : menuList){
            SysMenu parentMenu = sysMenuService.getById(sysMenu.getParentId());
            if(parentMenu != null){
                sysMenu.setParentName(parentMenu.getName());
            }
        }

        return R.ok().put("menuList", menuList);
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select(){
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId){
        SysMenu menu = sysMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @RSysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RSysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenu menu){
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RSysLog("删除菜单")
    @PostMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@RequestBody long menuId){
        if(menuId <= 18){
            return R.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
        if(menuList.size() > 0){
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenu menu){
        if(StringUtils.isBlank(menu.getName())){
            throw new MyException("菜单名称不能为空");
        }

        if(menu.getParentId() == null){
            throw new MyException("上级菜单不能为空");
        }

        //菜单
        if(menu.getType() == Constants.MenuType.MENU.getValue()){
            if(StringUtils.isBlank(menu.getUrl())){
                throw new MyException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constants.MenuType.CATALOG.getValue();
        if(menu.getParentId() != 0){
            SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if(menu.getType() == Constants.MenuType.CATALOG.getValue() ||
                menu.getType() == Constants.MenuType.MENU.getValue()){
            if(parentType != Constants.MenuType.CATALOG.getValue()){
                throw new MyException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(menu.getType() == Constants.MenuType.BUTTON.getValue()){
            if(parentType != Constants.MenuType.MENU.getValue()){
                throw new MyException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }

}

