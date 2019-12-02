package com.quasar.backend.modules.job.entity;

import lombok.Data;

@Data
public class TaskMinuteStat {

    private String date;

    private Integer cnt;

    private String beanName;
}
