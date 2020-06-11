package com.ethan.ryds.controller;

import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dao.SysUserMapper;
import com.ethan.ryds.entity.SysUser;
import com.ethan.ryds.service.SysUserService;
import com.ethan.ryds.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping("/save")
    public R save() {
        SysUser sysUser = new SysUser();
        sysUser.setUsername("test2");
        sysUser.setPassword("test");
        sysUser.setEmail("test@admin.io");
        sysUser.setMobile(18888888888L);
        sysUser.setStatus(1);
        sysUser.setCreateUserId(1L);

        boolean save = sysUserService.saveUser(sysUser);

        return save ? R.ok() : R.error(100, "添加失败");
    }

    @RequestMapping("/list")
    public R list() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        return R.ok().put("users", sysUsers);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info(){
        SysUser user = getCurrUser();
        System.out.println(user);
        return R.ok().put("user", user);
    }

}
