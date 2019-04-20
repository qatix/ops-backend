package com.quasar.backend.modules.job.utils;

import com.quasar.backend.common.utils.SpringContextUtils;
import com.quasar.backend.modules.job.entity.ScheduleJobEntity;
import com.quasar.backend.modules.job.entity.ScheduleJobLogEntity;
import com.quasar.backend.modules.job.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {
    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) context.getMergedJobDataMap()
                .get(ScheduleJobEntity.JOB_PARAM_KEY);

        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLogEntity sjlEntity = new ScheduleJobLogEntity();
        sjlEntity.setJobId(scheduleJob.getJobId());
        sjlEntity.setBeanName(scheduleJob.getBeanName());
        sjlEntity.setMethodName(scheduleJob.getMethodName());
        sjlEntity.setParams(scheduleJob.getParams());
        sjlEntity.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            log.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            sjlEntity.setTimes((int) times);
            //任务状态    0：成功    1：失败
            sjlEntity.setStatus(0);

            log.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            log.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            sjlEntity.setTimes((int) times);

            //任务状态    0：成功    1：失败
            sjlEntity.setStatus(1);
            sjlEntity.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(sjlEntity);
        }
    }
}
