package com.quasar.backend.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Author: xionghui
 * @Date: 2018/9/25 上午11:43
 */


public class SortUtil {
    public static void setSortParam(Page page, String sortParam, String order) {
        if ("descending".equals(order)) {
            page.setDescs(sortParam);
        } else {
            page.setAscs(sortParam);
        }
    }
}
