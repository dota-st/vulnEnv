package com.vuln.shirodemo.Controller;

import com.vuln.shirodemo.Shiro.ShiroUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by dotast on 2022/10/14 15:03
 */
@Controller
public class UserController {

    @PostMapping("/doLogin")
    public String doLoginPage(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(name="rememberMe", defaultValue="") Boolean rememberMe){
        if(ShiroUtil.login(username, password, rememberMe)){
            if(ShiroUtil.hasRole("admin")){
                return "redirect:/admin";
            } else if (ShiroUtil.hasRole("user")) {
                return "redirect:/user";
            }
        }
        return "redirect:/unauth";
    }

    @RequestMapping(value={"/"})
    public String helloPage() throws Exception {

        return "redirect:/login";
    }

    @RequestMapping(value={"/unauth"})
    public String errorPage() {
        return "unauth";
    }

    @RequestMapping(value={"/login"})
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value={"/logout"})
    public String logoutPage() {
        ShiroUtil.logout();
        return "login";
    }

    @RequestMapping(value={"/admin"})
    public String adminPage() {
        if(ShiroUtil.hasRole("admin")){
            return "admin";
        }
        return "login";
    }

    @RequestMapping(value={"/user"})
    public String userPage() {
        return "user";
    }
}
