package com.quasar.backend.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.Query;
import com.quasar.backend.modules.base.dao.ProductDao;
import com.quasar.backend.modules.base.entity.ProductEntity;
import com.quasar.backend.modules.base.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductEntity> implements ProductService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductEntity> page = this.selectPage(
                new Query<ProductEntity>(params).getPage(),
                new QueryWrapper<ProductEntity>()
        );

        return new PageUtils(page);
    }

}
