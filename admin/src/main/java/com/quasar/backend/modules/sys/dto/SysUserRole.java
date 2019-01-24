package com.quasar.backend.modules.sys.dto;

import com.alibaba.fastjson.annotation.JSONField;

public class SysUserRole {

    @JSONField(name = "id")
    private Long roleId;

    @JSONField(name = "name")
    private String roleName;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "SysUserRole{" +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
