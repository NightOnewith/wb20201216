package com.ethan.ryds.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ethan.ryds.dto.SysUserUpdatePwdForm;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.common.utils.PageUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ASUS on 2020/5/30.
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加用户
     */
    boolean saveUser(SysUser sysUser);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 获取用户角色
     */
    Set<String> getUserRole(long userId);

    /**
     * 修改用户
     */
    void update(SysUser user);

    /**
     * 修改用户密码
     */
    boolean updateUserPwd(SysUserUpdatePwdForm form);

    /**
     * 导入学生
     */
    int importStudents(SysUser[] list);

    /**
     * 批量添加学生到班级
     */
    int importToClass(String[] ids);

}
