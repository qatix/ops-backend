package com.quasar.backend.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.Query;
import com.quasar.backend.modules.sys.dao.SysLogDao;
import com.quasar.backend.modules.sys.entity.SysLogEntity;
import com.quasar.backend.modules.sys.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");

        IPage<SysLogEntity> page = this.page(
                new Query<SysLogEntity>(params).getPage(),
                new QueryWrapper<SysLogEntity>()
                        .like(StringUtils.isNotBlank(key), "username", key)
                        .or().like(StringUtils.isNotBlank(key), "operation", key)
        );

        return new PageUtils(page);
    }
}
