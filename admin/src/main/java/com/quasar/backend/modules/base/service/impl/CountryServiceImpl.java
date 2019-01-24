package com.quasar.backend.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.Query;
import com.quasar.backend.modules.base.dao.CountryDao;
import com.quasar.backend.modules.base.entity.CountryEntity;
import com.quasar.backend.modules.base.service.CountryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("countryService")
public class CountryServiceImpl extends ServiceImpl<CountryDao, CountryEntity> implements CountryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");

        IPage<CountryEntity> page = this.selectPage(
                new Query<CountryEntity>(params).getPage(),
                new QueryWrapper<CountryEntity>()
                        .like(StringUtils.isNotBlank(name), "zh_name", name)
                        .or()
                        .like(StringUtils.isNotBlank(name), "en_name", name)
                        .or()
                        .like(StringUtils.isNotBlank(name), "code", name)
        );

        return new PageUtils(page);
    }

}
