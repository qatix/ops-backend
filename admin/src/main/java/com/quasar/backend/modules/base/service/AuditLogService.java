package com.quasar.backend.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.modules.base.entity.AuditLogEntity;

import java.util.Map;

/**
 * 审核日志
 *
 * @author Logan
 * @date 2020-06-13 18:59:50
 */
public interface AuditLogService extends IService<AuditLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

