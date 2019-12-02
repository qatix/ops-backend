package com.quasar.backend;

import com.quasar.backend.common.utils.RedisUtils;
import com.quasar.backend.modules.job.dao.ScheduleJobLogDao;
import com.quasar.backend.modules.job.dto.FilterParam;
import com.quasar.backend.modules.job.entity.TaskHourStat;
import com.quasar.backend.modules.job.entity.TaskMinuteStat;
import com.quasar.backend.modules.sys.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatTaskTest {
    @Resource
    private ScheduleJobLogDao scheduleJobLogDao;

    @Test
    public void doTaskTest() {
        FilterParam fp = new FilterParam();
        fp.setStartDate("2019-11-01 00:00:00");
        fp.setEndDate("2019-12-12 00:00:00");

        List<TaskHourStat> hourStatList = scheduleJobLogDao.selectCountByHour(fp);
        log.info(hourStatList.toString());
        System.out.println(hourStatList);
    }

    @Test
    public void doTaskMinuteTest() {
        FilterParam fp = new FilterParam();
        fp.setStartDate("2019-11-01 00:00:00");
        fp.setEndDate("2019-12-12 00:00:00");

        List<TaskMinuteStat> minuteStats = scheduleJobLogDao.selectCountByMinute(fp);
        log.info(minuteStats.toString());
        System.out.println(minuteStats);
    }

}
