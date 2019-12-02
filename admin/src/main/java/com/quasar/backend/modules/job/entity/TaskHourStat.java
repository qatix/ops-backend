package com.quasar.backend.modules.job.entity;

import lombok.Data;

@Data
public class TaskHourStat {

    private String date;

    private Integer cnt;

    private String beanName;
}
