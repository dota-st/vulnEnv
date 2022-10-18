package com.vuln.shirodemo.Shiro;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dotast on 2022/10/14 15:13
 */
@Configuration
public class ShiroConfig {

    @Bean
    public IniRealm getIniRealm(){
        return new IniRealm("classpath:realm.ini");
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //cookie生效时间30天,单位秒;
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }


    @Bean
    CookieRememberMeManager RememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    DefaultWebSecurityManager securityManager(IniRealm iniRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm((Realm)iniRealm);
        manager.setRememberMeManager(RememberMeManager());
        return manager;
    }


    /*
     * anno：无需认证就可以访问
     * authc：必须认证才能访问
     * user：必须拥有记住我功能才能访问
     * perms：拥有某个资源的权限才能访问
     * role：拥有某个角色的权限才能访问
     * */
    @Bean
    ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl("/login");
        // 未授权界面
        //bean.setUnauthorizedUrl("/unauth");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("/admin", "authc, roles[admin]");
        map.put("/user", "authc, roles[user]");
        map.put("/**", "anon");
        bean.setFilterChainDefinitionMap(map);
        return bean;
    }
}
