package com.ethan.ryds.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.dao.sys.SysUserMapper;
import com.ethan.ryds.dto.SysUserUpdatePwdForm;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.service.sys.SysUserRoleService;
import com.ethan.ryds.service.sys.SysUserService;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by ASUS on 2020/5/30.
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 拼接查询条件
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        if (!params.get("username").toString().isEmpty()) {
            queryWrapper.like(SysUser::getUsername, params.get("username"));
        }
        if (!params.get("mobile").toString().isEmpty()) {
            queryWrapper.like(SysUser::getMobile, params.get("mobile"));
        }
        if ("已删除".equals(params.get("status"))) {
            queryWrapper.eq(SysUser::getStatus, 2);
        } else {
            queryWrapper.notIn(SysUser::getStatus, 2);
        }
        queryWrapper.orderByAsc(SysUser::getUserId);

        // 设置分页参数
        IPage<SysUser> page = this.page(new Query<SysUser>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

    @Override
    @Transactional
    public boolean saveUser(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        sysUser.setIntroduction("此用户很懒~，未填写个人简介。");
        // sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        // 默认密码
        sysUser.setPassword(new Sha256Hash("123456", salt).toHex());
        sysUser.setSalt(salt);

        // 保存用户
        boolean save = this.save(sysUser);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(sysUser.getUserId(), sysUser.getRoleIdList());

        return save;
    }

    @Override
    @Transactional
    public void update(SysUser user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public Set<String> getUserRole(long userId) {
        List<String> userRoleList = sysUserMapper.getUserRole(userId);

        //用户角色列表
        Set<String> userRoleSet = new HashSet<>();
        for(String role : userRoleList){
            if(StringUtils.isBlank(role)){
                continue;
            }
            userRoleSet.addAll(Arrays.asList(role.trim().split(",")));
        }

        return userRoleSet;
    }

    @Override
    public boolean updateUserPwd(SysUserUpdatePwdForm form) {
        SysUser sysUser = sysUserMapper.selectById(form.getUserId());

        SysUser user = new SysUser();
        user.setUserId(form.getUserId());
        user.setUsername(form.getUsername());
        user.setPassword(new Sha256Hash(form.getPassword(), sysUser.getSalt()).toHex());

        boolean flag = this.updateById(user);

        return flag;
    }

}
