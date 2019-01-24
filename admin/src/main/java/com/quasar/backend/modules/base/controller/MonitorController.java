package com.quasar.backend.modules.base.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Slf4j
@RestController
@RequestMapping("base/monitor")
public class MonitorController {

    @GetMapping("/stack")
    public String printStack() {
        log.info("printStack");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread.getAllStackTraces().entrySet()) {

            Thread thread = stackTrace.getKey();
            StackTraceElement[] stack = stackTrace.getValue();
            if (thread.equals(Thread.currentThread())) {
                continue;
            }

            sb.append("\nThread-" + thread.getId() + "-" + thread.getName() + "\n");
            for (StackTraceElement element : stack) {
                sb.append("\t" + element + "\n");
            }
        }

        return sb.toString().replaceAll("\\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").replaceAll("\\n", "<br/>");
    }
}
