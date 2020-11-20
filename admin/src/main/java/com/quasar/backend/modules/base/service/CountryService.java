package com.quasar.backend.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.modules.base.entity.CountryEntity;

import java.util.Map;

/**
 * 国家
 *
 * @author Logan
 * @date 2018-08-23 21:10:23
 */
public interface CountryService extends IService<CountryEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

