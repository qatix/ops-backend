package com.quasar.backend.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
