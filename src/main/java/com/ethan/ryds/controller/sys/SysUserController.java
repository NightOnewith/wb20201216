package com.ethan.ryds.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.ryds.common.log.annotation.RSysLog;
import com.ethan.ryds.common.constant.Constants;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dao.sys.SysUserMapper;
import com.ethan.ryds.dto.StuUpdatePwdForm;
import com.ethan.ryds.dto.SysUserUpdatePwdForm;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.service.sys.SysUserRoleService;
import com.ethan.ryds.service.sys.SysUserService;
import com.ethan.ryds.common.utils.PageUtils;
import com.ethan.ryds.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * 学生注册
     */
    @RequestMapping("/register")
    public R register(@RequestBody SysUser sysUser) {
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error("用户名已存在");
        }

        // 学生自己创建
        sysUser.setCreateUserId(0L);
        sysUser.setPosition(2);
        sysUser.setStatus(1);
        boolean save = sysUserService.saveUser(sysUser);

        return save ? R.ok("注册成功，请返回登录。") : R.error("账号注册失败，请重试！");
    }

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

        if (sysUser.getPosition() == 1) {
            List<String> classmateList = sysUser.getClassmateList();
            String classmate = "";
            if (classmateList.size() > 0 && classmateList != null) {
                for (int i = 0; i < classmateList.size(); i++) {
                    if (i == classmateList.size() - 1) {
                        classmate += classmateList.get(i);
                    } else {
                        classmate += classmateList.get(i) + ",";
                    }
                }
                sysUser.setClassmate(classmate);
            } else {
                sysUser.setClassmate(classmate);
            }
        }

        sysUser.setCreateUserId(getCurrUserId());
        boolean save = sysUserService.saveUser(sysUser);

        return save ? R.ok() : R.error(100, "添加失败");
    }

    /**
     * 保存用户
     */
    @RSysLog("保存教师用户")
    @RequestMapping("/teasave")
    @RequiresPermissions("sys:user:teasave")
    public R teasave(@RequestBody SysUser sysUser) {
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        if (sysUser.getPosition() == 1) {
            List<String> classmateList = sysUser.getClassmateList();
            String classmate = "";
            if (classmateList.size() > 0 && classmateList != null) {
                for (int i = 0; i < classmateList.size(); i++) {
                    if (i == classmateList.size() - 1) {
                        classmate += classmateList.get(i);
                    } else {
                        classmate += classmateList.get(i) + ",";
                    }
                }
                sysUser.setClassmate(classmate);
            } else {
                sysUser.setClassmate(classmate);
            }
        }

        sysUser.setCreateUserId(getCurrUserId());
        boolean save = sysUserService.saveUser(sysUser);

        return save ? R.ok() : R.error(100, "添加失败");
    }

    /**
     * 保存学生用户
     */
    @RSysLog("保存学生用户")
    @RequestMapping("/stusave")
    @RequiresPermissions("sys:user:stusave")
    public R stusave(@RequestBody SysUser sysUser) {
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        if (sysUser.getPosition() == 1) {
            List<String> classmateList = sysUser.getClassmateList();
            String classmate = "";
            if (classmateList.size() > 0 && classmateList != null) {
                for (int i = 0; i < classmateList.size(); i++) {
                    if (i == classmateList.size() - 1) {
                        classmate += classmateList.get(i);
                    } else {
                        classmate += classmateList.get(i) + ",";
                    }
                }
                sysUser.setClassmate(classmate);
            } else {
                sysUser.setClassmate(classmate);
            }
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

        if (user.getPosition() == 1) {
            List<String> classmateList = user.getClassmateList();
            String classmate = "";
            if (classmateList != null) {
                for (int i = 0; i < classmateList.size(); i++) {
                    if (i == classmateList.size() - 1) {
                        classmate += classmateList.get(i);
                    } else {
                        classmate += classmateList.get(i) + ",";
                    }
                }
                user.setClassmate(classmate);
            } else {
                user.setClassmate(classmate);
            }
        }

        user.setCreateUserId(getCurrUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 修改教师用户
     */
    @RSysLog("修改教师用户")
    @PostMapping("/teaupdate")
    @RequiresPermissions("sys:user:teaupdate")
    public R teaupdate(@RequestBody SysUser user){
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, user.getUsername())
                .notIn(SysUser::getUserId, user.getUserId());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        if (user.getPosition() == 1) {
            List<String> classmateList = user.getClassmateList();
            String classmate = "";
            if (classmateList != null) {
                for (int i = 0; i < classmateList.size(); i++) {
                    if (i == classmateList.size() - 1) {
                        classmate += classmateList.get(i);
                    } else {
                        classmate += classmateList.get(i) + ",";
                    }
                }
                user.setClassmate(classmate);
            } else {
                user.setClassmate(classmate);
            }
        }

        user.setCreateUserId(getCurrUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 修改学生用户
     */
    @RSysLog("修改学生用户")
    @PostMapping("/stuupdate")
    @RequiresPermissions("sys:user:stuupdate")
    public R stuupdate(@RequestBody SysUser user){
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, user.getUsername())
                .notIn(SysUser::getUserId, user.getUserId());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        if (user.getPosition() == 1) {
            List<String> classmateList = user.getClassmateList();
            String classmate = "";
            if (classmateList != null) {
                for (int i = 0; i < classmateList.size(); i++) {
                    if (i == classmateList.size() - 1) {
                        classmate += classmateList.get(i);
                    } else {
                        classmate += classmateList.get(i) + ",";
                    }
                }
                user.setClassmate(classmate);
            } else {
                user.setClassmate(classmate);
            }
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
    @RequiresPermissions("sys:user:update")
    public R updatePwd(@RequestBody SysUserUpdatePwdForm form) {
        boolean b = sysUserService.updateUserPwd(form);

        return b ? R.ok() : R.error(100,"修改密码失败");
    }

    /**
     * 修改教师密码
     */
    @RSysLog("修改教师密码")
    @RequestMapping("/updateTeaPwd")
    @RequiresPermissions("sys:user:teaupdate")
    public R updateTeaPwd(@RequestBody SysUserUpdatePwdForm form) {
        boolean b = sysUserService.updateUserPwd(form);

        return b ? R.ok() : R.error(100,"修改密码失败");
    }

    /**
     * 修改学生密码
     */
    @RSysLog("修改学生密码")
    @RequestMapping("/updateStudentPwd")
    @RequiresPermissions("sys:user:stuupdate")
    public R updateStuPwd(@RequestBody SysUserUpdatePwdForm form) {
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
     * 删除教师用户 (逻辑删除)
     */
    @RSysLog("删除教师用户")
    @PostMapping("/teadelete")
    @RequiresPermissions("sys:user:teadelete")
    public R teadelete(@RequestBody Long userId){
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
     * 删除学生用户 (逻辑删除)
     */
    @RSysLog("删除学生用户")
    @PostMapping("/studelete")
    @RequiresPermissions("sys:user:studelete")
    public R studelete(@RequestBody Long userId){
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
     * 获取管理员用户列表
     */
    @RequestMapping("/adminList")
    @RequiresPermissions("sys:user:list")
    public R adminList(@RequestParam Map<String, Object> params){
        params.put("position", 0);
        PageUtils page = sysUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取教师用户列表
     */
    @RequestMapping("/teacherList")
    @RequiresPermissions("sys:user:tealist")
    public R teacherList(@RequestParam Map<String, Object> params){
        params.put("position", 1);
        PageUtils page = sysUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取学生用户列表
     */
    @RequestMapping("/studentList")
    @RequiresPermissions("sys:user:stulist")
    public R studentList(@RequestParam Map<String, Object> params){
        params.put("position", 2);
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

        // 封装老师的班级列表
        if (user.getPosition() == 1) {
            if (!user.getClassmate().isEmpty() && user.getClassmate() != null) {
                List<String> classmates = new ArrayList<>();
                String classmate = user.getClassmate();
                String[] split = classmate.split(",");
                for (int i = 0; i < split.length; i++) {
                    classmates.add(split[i]);
                }
                user.setClassmateList(classmates);
            }
        }

        return R.ok().put("user", user);
    }

    /**
     * 获取修改教师信息
     */
    @GetMapping("/teaInfo/{userId}")
    @RequiresPermissions("sys:user:teainfo")
    public R teaInfo(@PathVariable("userId") Long userId){
        SysUser user = sysUserService.getById(userId);

        // 获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        // 封装老师的班级列表
        if (user.getPosition() == 1) {
            if (!user.getClassmate().isEmpty() && user.getClassmate() != null) {
                List<String> classmates = new ArrayList<>();
                String classmate = user.getClassmate();
                String[] split = classmate.split(",");
                for (int i = 0; i < split.length; i++) {
                    classmates.add(split[i]);
                }
                user.setClassmateList(classmates);
            }
        }

        return R.ok().put("user", user);
    }

    /**
     * 获取修改学生信息
     */
    @GetMapping("/stuInfo/{userId}")
    @RequiresPermissions("sys:user:stuinfo")
    public R stuInfo(@PathVariable("userId") Long userId){
        SysUser user = sysUserService.getById(userId);

        // 获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        // 封装老师的班级列表
        if (user.getPosition() == 1) {
            if (!user.getClassmate().isEmpty() && user.getClassmate() != null) {
                List<String> classmates = new ArrayList<>();
                String classmate = user.getClassmate();
                String[] split = classmate.split(",");
                for (int i = 0; i < split.length; i++) {
                    classmates.add(split[i]);
                }
                user.setClassmateList(classmates);
            }
        }

        return R.ok().put("user", user);
    }

    /**
     * 获取班级学生列表
     */
    @GetMapping("/classmateStu/{classmate}")
    @RequiresPermissions("sys:user:stulist")
    public R getStudentsByClassId(@PathVariable("classmate") String classmate){
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getPosition, 2);
        queryWrapper.eq(SysUser::getClassmate, classmate);
        queryWrapper.eq(SysUser::getStatus, 1);

        List<SysUser> users = sysUserService.list(queryWrapper);

        return R.ok().put("list", users);
    }

    /**
     * 导入Excel模板
     */
    @RequestMapping("/import")
    @RequiresPermissions("sys:user:import")
    public R importExcel(@RequestBody SysUser[] list) {
        int count = sysUserService.importStudents(list);

        return R.ok("成功导入【" + count + "】条学生信息");
    }

    /**
     * 批量导入学生到班级
     */
    @RequestMapping("/importToClass")
    @RequiresPermissions("sys:user:uploadtoclass")
    public R importToClass(@RequestBody String[] ids) {
        int count = sysUserService.importToClass(ids);

        return R.ok("成功导入【" + count + "】条学生信息");
    }

    /**
     * 学生个人中心
     */
    @GetMapping("/personal/{userId}")
    public R personal(@PathVariable("userId") Long userId) {
        SysUser user = sysUserService.getById(userId);

        return R.ok().put("user", user);
    }

    /**
     * 修改用户
     */
    @PostMapping("/updateUserInfo")
    public R updateUserInfo(@RequestBody SysUser user){
        // 校验用户名是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, user.getUsername())
                .notIn(SysUser::getUserId, user.getUserId());

        List<SysUser> sysUsers = sysUserMapper.selectList(queryWrapper);
        if (sysUsers.size() > 0) {
            return R.error(100, "用户名已存在");
        }

        boolean update = sysUserService.saveOrUpdate(user);

        return update ? R.ok("修改信息成功") : R.error("修改信息失败");
    }

    /**
     * 学生修改密码
     */
    @PostMapping("/updateStuPwd")
    public R updateStuPwd(@RequestBody StuUpdatePwdForm form){
        SysUser sysUser = sysUserService.getById(form.getUserId());

        String oldPassword = new Sha256Hash(form.getOldPassword(), sysUser.getSalt()).toHex();
        if (!oldPassword.equals(sysUser.getPassword())) {
            return R.error("旧密码与原密码不相同！");
        }

        SysUser user = new SysUser();
        user.setUserId(form.getUserId());
        user.setPassword(new Sha256Hash(form.getNewPassword(), sysUser.getSalt()).toHex());
        boolean update = sysUserService.saveOrUpdate(user);

        return update ? R.ok("密码修改成功") : R.error("密码修改失败");
    }

}
