package com.ethan.ryds.common.oauth2;

import com.ethan.ryds.common.constant.Constants;
import com.ethan.ryds.common.utils.HttpContextUtils;
import com.ethan.ryds.common.utils.JWTUtils;
import com.ethan.ryds.common.utils.R;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * oauth2过滤器
 *
 */
public class OAuth2Filter extends AuthenticatingFilter {

    // 用户退出的token，存入黑名单，每次拦截请求判断token时，移除过期token
    public static List<String> tokenBlackList = new ArrayList();

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if(StringUtils.isBlank(token)){
            return null;
        }

        return new OAuth2Token(token);
    }

    // 拦截所有请求，拒绝访问
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }

        return false;
    }

    // 拒绝访问的请求，会调用该方法，先获取token进行校验，在调用executeLogin登录方法。
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);

        // 清理过期token
        clearExpireToken();

        if (tokenBlackList.contains(token)) {
            System.out.println("token 在黑名单中！！！");
        }

        // 校验token是否为空，或者是否存在黑名单中
        if(StringUtils.isBlank(token) || tokenBlackList.contains(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = new Gson().toJson(R.error(Constants.HttpStatus.USER_UNAUTHORIZED.getStatus(), Constants.HttpStatus.USER_UNAUTHORIZED.getMsg()));

            httpResponse.getWriter().print(json);

            return false;

        // 校验token是否合法
        } else if (JWTUtils.validateJWT(token).isSuccess()) {
            // executeLogin方法中调用了 subject.login(token);
            // 调用subject.login(token);方法会调用OAuth2Realm类中的doGetAuthenticationInfo方法进行登录。
            // 登录成功会调用OAuth2Realm类中的doGetAuthorizationInfo方法，进行权限授权操作，整个流程结束！
            // 登录失败会调用本类的onLoginFailure方法，整个流程结束！
            return executeLogin(request, response);

        // token不为空，且已过期
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = new Gson().toJson(R.error(Constants.HttpStatus.USER_UNAUTHORIZED.getStatus(), Constants.HttpStatus.USER_UNAUTHORIZED.getMsg()));

            httpResponse.getWriter().print(json);

            System.out.println("token 已过期！！！");

            return false;
        }
    }

    // 登录失败时调用
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            R r = R.error(Constants.HttpStatus.USER_UNAUTHORIZED.getStatus(), throwable.getMessage());

            String json = new Gson().toJson(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader("X-Token");

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter("X-Token");
        }

        return token;
    }

    /**
     * 清理token黑名单中的过期token
     */
    private void clearExpireToken() {
        // 当前时间
        Date now = new Date();

        Iterator<String> iterator = tokenBlackList.iterator();
        while (iterator.hasNext()) {
            String token = iterator.next();
            try {
                Claims claims = JWTUtils.parseJWT(token);
                Date expireDate = claims.getExpiration();
                if (expireDate.before(now)) {
                    iterator.remove();
                }
            } catch (Exception e) {
                iterator.remove();
            }
        }
    }

}
