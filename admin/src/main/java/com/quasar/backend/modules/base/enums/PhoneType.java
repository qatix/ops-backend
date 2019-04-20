package com.quasar.backend.modules.base.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * @author logan
 */
public enum PhoneType {
    UNKNOWN(0, "未知"),
    FEATURE_PHONE(4, "功能机"),
    START_PHONE(5, "智能机");

    @EnumValue
    private final Integer value;
    private final String desc;

    PhoneType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    //
//    @Override
//    public Integer getValue() {
//        return this.value;
//    }
}
