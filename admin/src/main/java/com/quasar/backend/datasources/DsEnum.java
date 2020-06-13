package com.quasar.backend.datasources;

/**
 * 数据源类型
 *
 * @Author: Logan
 * @Date: 2018/8/30 4:48 PM
 */
public enum DsEnum {
    /**
     * 默认数据源
     */
    FIRST("first");

    private String value;

    DsEnum(java.lang.String value) {
        this.value = value;
    }

    public java.lang.String getValue() {
        return value;
    }
}
