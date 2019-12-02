package com.quasar.backend.modules.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.quasar.backend.modules.job.dto.FilterParam;
import com.quasar.backend.modules.job.entity.ScheduleJobLogEntity;
import com.quasar.backend.modules.job.entity.TaskHourStat;
import com.quasar.backend.modules.job.entity.TaskMinuteStat;

import java.util.List;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {

    List<TaskHourStat> selectCountByHour(FilterParam param);


    List<TaskMinuteStat> selectCountByMinute(FilterParam param);

}
