package com.ethan.ryds.common.conf;

import com.ethan.ryds.common.oauth2.OAuth2Filter;
import com.ethan.ryds.common.oauth2.OAuth2Realm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 *
 */
@Configuration
public class ShiroConfig {

    @Bean("securityManager")
    public SecurityManager securityManager(OAuth2Realm oAuth2Realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(oAuth2Realm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        //oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("oauth2", new OAuth2Filter());
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = new LinkedHashMap<>();
        //filterMap.put("/webjars/**", "anon");
        //filterMap.put("/druid/**", "anon");
        //filterMap.put("/app/**", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/uploadPicWord/pic", "anon");
        filterMap.put("/uploadPicWord/word", "anon");
        filterMap.put("/uploadPicWord/uploadImg", "anon");
        filterMap.put("/studentScore/export", "anon");

        /*===================== 用户端请求不拦截接口 ======================*/
        filterMap.put("/experDes/select", "anon");
        filterMap.put("/experDes/gxxy", "anon");
        filterMap.put("/experDes/syxmjs", "anon");
        filterMap.put("/experDes/descinfo/**", "anon");
        filterMap.put("/teachingTeam/select", "anon");
        filterMap.put("/teachingTeam/teacherinfo/**", "anon");
        filterMap.put("/page/view/index", "anon");
        filterMap.put("/page/view/count", "anon");
        filterMap.put("/commentTopic/list", "anon");
        filterMap.put("/evaluateInfo/list", "anon");
        filterMap.put("/sys/user/register", "anon");


        /*=============================================================*/
        //filterMap.put("/swagger/**", "anon");
        //filterMap.put("/v2/api-docs", "anon");
        //filterMap.put("/swagger-ui.html", "anon");
        //filterMap.put("/swagger-resources/**", "anon");
        //filterMap.put("/captcha.jpg", "anon");
        //filterMap.put("/aaa.txt", "anon");
        filterMap.put("/static/**", "anon");
        filterMap.put("/upload/**", "anon"); // 上传文件访问路径
        filterMap.put("/**", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);
        // 设置登录的请求（如果请求被拦截则跳转到登录页面）
        //shiroFilter.setLoginUrl("/toLogin");
        // 未经授权页面
        //shiroFilter.setUnauthorizedUrl("/noauth");

        return shiroFilter;
    }

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
