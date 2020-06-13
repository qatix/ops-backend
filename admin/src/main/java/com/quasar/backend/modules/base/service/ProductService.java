package com.quasar.backend.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.modules.base.entity.ProductEntity;

import java.util.Map;

/**
 * 商品
 *
 * @author Logan
 *
 * @date 2018-10-13 11:11:14
 */
public interface ProductService extends IService<ProductEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

