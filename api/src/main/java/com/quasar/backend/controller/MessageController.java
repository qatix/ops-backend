package com.quasar.backend.controller;

import com.quasar.backend.common.utils.R;
import com.quasar.backend.common.validator.ValidatorUtils;
import com.quasar.backend.entity.Task;
import com.quasar.backend.service.MqService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/message")
@Api(tags = "MQ接口")
public class MessageController {

    @Autowired
    private MqService mqService;

    @GetMapping("/test")
    public R test() {
        return R.ok().put("msg", "test api。。。");
    }

    @PostMapping("/send")
    @ApiOperation("发送任务")
    public R sendTask(@RequestBody Task task) {
        //表单校验
        ValidatorUtils.validateEntity(task);

        log.info("task:" + task.toString());

        String tags = task.getTags() == null ? "" : task.getTags();

        boolean ret = mqService.sendTask(task.getTaskKey(), tags, task.getData());

        return R.ok().put("task", task).put("ret", ret);
    }

    @PostMapping("/send-async")
    @ApiOperation("发送任务")
    public R sendTaskAsync(@RequestBody Task task) {
        //表单校验
        ValidatorUtils.validateEntity(task);

        log.info("task:" + task.toString());

        String tags = task.getTags() == null ? "" : task.getTags();

        mqService.sendTaskAsync(task.getTaskKey(), tags, task.getData());

        return R.ok().put("task", task);
    }


}
