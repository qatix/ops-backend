package com.quasar.backend.modules.sys.controller;

import com.quasar.backend.common.utils.R;
import com.quasar.backend.modules.sys.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


/**
 * 供应商
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-08-13 20:09:11
 */
@RestController
@RequestMapping("sys/test")
public class TestController extends AbstractController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/set")
    public R set(@RequestParam String key, @RequestParam String value) {
        ShiroUtils.getSession().setAttribute(key, value);
        return R.ok().put("data", ShiroUtils.getSession().getAttributeKeys());
    }

    @GetMapping("/get")
    public R get(@RequestParam String key) {
        Object value = ShiroUtils.getSession().getAttribute(key);
        return R.ok().put(key, value);
    }

    @GetMapping("/sess")
    public R get() {
        Object value = ShiroUtils.getSession().getId();
        return R.ok().put("sid", value);
    }

    @GetMapping("/get_ses")
    public R getSes(String sid) {
        Object value = redisTemplate.opsForValue().get(sid);
        return R.ok().put("ses", value);
    }

    @GetMapping("/get_user")
    public R getSesUser() {
        return R.ok().put("data", getUser());
    }

    @GetMapping("/get_subject")
    public R getSubject() {
        return R.ok().put("data", SecurityUtils.getSubject());
    }

    @GetMapping("/get_principal")
    public R getPrincipal() {
        return R.ok().put("data", SecurityUtils.getSubject().getPrincipal());
    }

    @GetMapping("/gets")
    public R gets() {
        Collection<Object> colls = ShiroUtils.getSession().getAttributeKeys();
        HashMap<Object, Object> r = new HashMap<>(16);
        for (Object key : colls) {
            r.put(key, ShiroUtils.getSession().getAttribute(key));
        }
        return R.ok().put("data", r);
    }

    @RequestMapping("/del")
    public R del(@RequestParam String key) {
        ShiroUtils.getSession().removeAttribute(key);
        return R.ok().put("data", ShiroUtils.getSession().getAttributeKeys());
    }

    @GetMapping("/gets_list")
    public Object getList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("dfkasdkasdkf");
        stringList.add("cdccsdd");
        stringList.add("c01202 dsfsdkf");
        return stringList;
    }

    public Boolean hasPermission(String permission) {
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }

    @RequestMapping("/perms")
    public R permissionCheck(@RequestParam String key) {
        return R.ok().put("data", hasPermission(key));
    }

    @GetMapping("/test_exp")
    public R testExp() {
        throw new AuthenticationException("test exctp");
    }
}
