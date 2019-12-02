package com.quasar.backend.modules.job.task;

import com.quasar.backend.modules.job.dao.ScheduleJobLogDao;
import com.quasar.backend.modules.job.dto.FilterParam;
import com.quasar.backend.modules.job.entity.TaskHourStat;
import com.quasar.backend.modules.job.entity.TaskMinuteStat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component("statTask")
public class StatTask {

    @Resource
    private ScheduleJobLogDao scheduleJobLogDao;


    public void doTask(){
        FilterParam fp = new FilterParam();
        fp.setStartDate("2019-11-01 00:00:00");
        fp.setEndDate("2019-12-12 00:00:00");

        List<TaskHourStat> hourStatList = scheduleJobLogDao.selectCountByHour(fp);
        log.info(hourStatList.toString());
    }


    public void doMinuteTask(){
        FilterParam fp = new FilterParam();
        fp.setStartDate("2019-11-01 00:00:00");
        fp.setEndDate("2019-12-12 00:00:00");

        List<TaskMinuteStat> miniteStats = scheduleJobLogDao.selectCountByMinute(fp);
        log.info(miniteStats.toString());
    }
}
