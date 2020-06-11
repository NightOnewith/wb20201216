package com.ethan.ryds.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ethan.ryds.constant.Constants;
import com.ethan.ryds.dao.SysMenuMapper;
import com.ethan.ryds.dao.SysUserMapper;
import com.ethan.ryds.entity.SysMenu;
import com.ethan.ryds.entity.SysUser;
import com.ethan.ryds.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by ASUS on 2020/5/30.
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public boolean saveUser(SysUser sysUser) {
        sysUser.setCreateTime(LocalDateTime.now());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        sysUser.setPassword(new Sha256Hash(sysUser.getPassword(), salt).toHex());
        sysUser.setSalt(salt);
        boolean save = this.save(sysUser);
        return save;
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constants.SUPER_ADMIN){
            List<SysMenu> menuList = sysMenuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
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
}
