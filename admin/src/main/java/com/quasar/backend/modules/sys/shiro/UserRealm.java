package com.quasar.backend.modules.sys.shiro;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quasar.backend.common.utils.Constant;
import com.quasar.backend.modules.sys.dao.SysMenuDao;
import com.quasar.backend.modules.sys.dao.SysUserDao;
import com.quasar.backend.modules.sys.dao.SysUserRoleDao;
import com.quasar.backend.modules.sys.dto.SysUserRole;
import com.quasar.backend.modules.sys.entity.SysMenuEntity;
import com.quasar.backend.modules.sys.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 认证
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 上午11:55:49
 */
@Slf4j
@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysMenuDao sysMenuDao;
    @Resource
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("doGetAuthorizationInfo:principals");

        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getId();

        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        //用户角色列表
        Set<String> rolesSet = new HashSet<>();
        List<SysUserRole> userRoleList = sysUserRoleDao.queryRolesList(userId);
        for (SysUserRole userRole : userRoleList) {
            rolesSet.add(userRole.getRoleName());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(rolesSet);
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        log.info("doGetAuthenticationInfo:" + authcToken);

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        log.info("doGetAuthenticationInfo - token:" + token.toString());

        //查询用户信息
        SysUserEntity user;
        user = sysUserDao.selectOne(
                new QueryWrapper<SysUserEntity>()
                        .eq("username", token.getUsername())
                        .or()
                        .eq("email", token.getUsername())
                        .or()
                        .eq("mobile", token.getUsername())
        );

        //账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }
}
