package com.quasar.backend.modules.base.enums;

/**
 * 审核结果类型
 */
public enum AuditResultType {
    none("none"),
    pass("pass"),
    reject("reject");

    private String val;

    private AuditResultType(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public static AuditResultType fromValue(String val) {
        for (AuditResultType type : values()
        ) {
            if (type.val.equals(val)) {
                return type;
            }
        }
        return null;
    }
}
