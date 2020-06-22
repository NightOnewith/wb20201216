package com.ethan.ryds.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.ryds.common.log.annotation.RSysLog;
import com.ethan.ryds.common.constant.Constants;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dao.sys.SysUserMapper;
import com.ethan.ryds.dto.SysUserUpdatePwdForm;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.service.sys.SysUserRoleService;
import com.ethan.ryds.service.sys.SysUserService;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * Created by ASUS on 2020/5/30.
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 保存用户
     */
    @RSysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUser sysUser) {
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        sysUser.setCreateUserId(getCurrUserId());

        boolean save = sysUserService.saveUser(sysUser);

        return save ? R.ok() : R.error(100, "添加失败");
    }

    /**
     * 修改用户
     */
    @RSysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUser user){
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, user.getUsername())
                .notIn(SysUser::getUserId, user.getUserId());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        user.setCreateUserId(getCurrUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 修改密码
     */
    @RSysLog("修改密码")
    @RequestMapping("/updatePwd")
    // @RequiresPermissions("sys:user:update")
    public R updatePwd(@RequestBody SysUserUpdatePwdForm form) {
        boolean b = sysUserService.updateUserPwd(form);

        return b ? R.ok() : R.error(100,"修改密码失败");
    }

    /**
     * 删除用户 (逻辑删除)
     */
    @RSysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long userId){
        if(Constants.SUPER_ADMIN == userId){
            return R.error("系统管理员不能删除");
        }

        if(getCurrUserId() == userId){
            return R.error("当前用户不能删除");
        }

        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setStatus(2);
        boolean b = sysUserService.updateById(sysUser);

        return b ? R.ok() : R.error(100, "删除失败");
    }

    /**
     * 获取用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info(){
        SysUser user = getCurrUser();
        return R.ok().put("user", user);
    }

    /**
     * 获取修改用户信息
     */
    @GetMapping("/userInfo/{userId}")
    @RequiresPermissions("sys:user:info")
    public R userInfo(@PathVariable("userId") Long userId){
        SysUser user = sysUserService.getById(userId);

        // 获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

}
