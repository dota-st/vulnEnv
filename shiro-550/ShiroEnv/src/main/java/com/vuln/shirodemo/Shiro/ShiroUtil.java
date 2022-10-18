package com.vuln.shirodemo.Shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * Created by dotast on 2022/10/14 15:15
 */
public class ShiroUtil {

    //shiro的认证、授权操作都需要先将User包装为Subject，通过Subject来操作
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    public static boolean login(String username,String password, Boolean rememberMe) {
        Subject subject = SecurityUtils.getSubject();
        if(rememberMe == null){
            rememberMe=false;
        }
        if(!subject.isAuthenticated()){
            //用token封装用户信息
            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            try {
                subject.login(token);  //将token与Realm中的安全数据对比。返回值是void，如果找不到匹配，直接抛出异常
            } catch (AuthenticationException e) {  //Realm中没有匹配的用户
                return false;
            }
        }
        return true;
    }

    /**
     * 登出
     */
    public static void logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }


    /**
     * 检查用户是否拥有指定的角色
     * @param role
     * @return boolean 该用户是否具有指定的角色
     */
    public static boolean hasRole(String role) {
        Subject subject = SecurityUtils.getSubject();
        return subject.hasRole(role);
    }

}
