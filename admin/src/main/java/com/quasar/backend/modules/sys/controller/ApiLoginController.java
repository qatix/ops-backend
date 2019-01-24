package com.quasar.backend.modules.sys.controller;


import com.quasar.backend.common.utils.R;
import com.quasar.backend.modules.sys.entity.SysUserEntity;
import com.quasar.backend.modules.sys.form.LoginForm;
import com.quasar.backend.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Logan
 * @Date: 2018/8/21 5:17 PM
 */
@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:9527")
public class ApiLoginController {

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public R login(@RequestBody LoginForm loginForm) {
        System.out.println("form:" + loginForm.toString());

        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword());
            subject.login(token);
        } catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        } catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

        SysUserEntity user = ShiroUtils.getUserEntity();
        //组装返回的数据
        Map<String, Object> map = new HashMap<>();
        map.put("token", ShiroUtils.getSession().getId());
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("email", user.getEmail());
        map.put("mobile", user.getMobile());

        return R.ok().put("data", map);
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public R logout() {
        ShiroUtils.logout();
        return R.ok();
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R unauthorized() {
        return R.error(401, "Unauthorized");
    }
}
