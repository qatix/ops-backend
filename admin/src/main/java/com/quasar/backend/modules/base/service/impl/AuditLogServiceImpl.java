package com.quasar.backend.modules.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.Query;
import com.quasar.backend.modules.base.dao.AuditLogDao;
import com.quasar.backend.modules.base.entity.AuditLogEntity;
import com.quasar.backend.modules.base.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("auditLogService")
public class AuditLogServiceImpl extends ServiceImpl<AuditLogDao, AuditLogEntity> implements AuditLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AuditLogEntity> page = this.page(
                new Query<AuditLogEntity>(params).getPage(),
                new QueryWrapper<AuditLogEntity>()
        );

        return new PageUtils(page);
    }

}
