package com.quasar.backend.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.modules.base.entity.PhoneEntity;

import java.util.Map;

/**
 * 机型信息
 *
 * @author Logan
 * @email hawk418@qq.com
 * @date 2018-08-27 18:02:48
 */
public interface PhoneService extends IService<PhoneEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

