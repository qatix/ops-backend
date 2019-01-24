package com.quasar.backend.common.utils;

import java.math.BigDecimal;

/**
 * @Author: xionghui
 * @Date: 2018/9/14 下午2:57
 */
public class MathUtil {
    public static double convertAnyDecimal(double input, int decimalNum) {
        BigDecimal b = new BigDecimal(input);
        return b.setScale(decimalNum, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
