package com.quasar.backend.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Task {
    private Long id;

    @NotBlank(message = "taskKey不能为空")
    private String taskKey;

    private String tags;

    @NotBlank(message = "data不能为空")
    private String data;
}
