package com.ethan.ryds.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ethan.ryds.common.constant.Constants;
import com.ethan.ryds.controller.base.AbstractController;
import com.ethan.ryds.dto.SysLoginForm;
import com.ethan.ryds.entity.sys.SysUser;
import com.ethan.ryds.common.oauth2.OAuth2Filter;
import com.ethan.ryds.service.sys.SysUserService;
import com.ethan.ryds.common.utils.JWTUtils;
import com.ethan.ryds.common.utils.R;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * Created by ASUS on 2020/6/1.
 */
@RestController
@RequestMapping("/sys")
public class SysLoginController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(@RequestBody SysLoginForm form) {
        // 获取用户信息
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda().eq(SysUser::getUsername, form.getUsername());
        SysUser sysUser = sysUserService.getOne(queryWrapper);

        //账号不存在、密码错误
        if(null == sysUser || !sysUser.getPassword().equals(new Sha256Hash(form.getPassword(), sysUser.getSalt()).toHex())) {
            return R.error(Constants.HttpStatus.USER_NAME_OR_PASSWORD_ERROR.getStatus(), Constants.HttpStatus.USER_NAME_OR_PASSWORD_ERROR.getMsg());
        }

        //账号锁定
        if(0 == sysUser.getStatus()){
            return R.error(Constants.HttpStatus.USER_NAME_CLOCKED.getStatus(), Constants.HttpStatus.USER_NAME_CLOCKED.getMsg());
        }

        SysUser user = new SysUser();
        user.setUserId(sysUser.getUserId());
        user.setUsername(sysUser.getUsername());
        user.setStatus(sysUser.getStatus());
        user.setCreateTime(sysUser.getCreateTime());

        //生成token，返回给前端
        String token = JWTUtils.createToken(UUID.randomUUID().toString(),
                sysUser.getUsername(),
                JWTUtils.generalSubject(user),
                Constants.Jwt.JWT_EXPIRE_TTL.getValue());

        return R.ok().put("token", token).put("userId", user.getUserId());
    }

    @RequestMapping("/logout")
    public R logout(HttpServletRequest request) {
        // 获取token放入黑名单
        String token = request.getHeader("X-Token");
        List<String> tokenBlackList = OAuth2Filter.tokenBlackList;
        tokenBlackList.add(token);
        // 获取当前用户
        Subject currUserSubject = getCurrUserSubject();
        // 登出
        currUserSubject.logout();

        return R.ok();
    }
}
