package com.quasar.backend.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.Query;
import com.quasar.backend.modules.base.dao.PhoneDao;
import com.quasar.backend.modules.base.entity.PhoneEntity;
import com.quasar.backend.modules.base.service.PhoneService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("phoneService")
public class PhoneServiceImpl extends ServiceImpl<PhoneDao, PhoneEntity> implements PhoneService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<PhoneEntity> page = this.page(
                new Query<PhoneEntity>(params).getPage(),
                new QueryWrapper<PhoneEntity>()
                        .like(StringUtils.isNotBlank(key), "channel", key)
                        .or()
                        .like(StringUtils.isNotBlank(key), "code", key)
        );

        return new PageUtils(page);
    }

}
