package com.quasar.backend.controller;

import com.google.common.base.Stopwatch;
import com.quasar.backend.common.utils.MapUtils;
import com.quasar.backend.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequestMapping("/memtest")
@Slf4j
@RestController
public class MemoryTstController {

    public class BigObject{
        byte[] data;
        public BigObject(int size){
            data = new byte[size];
        }
    }

    @GetMapping("/createObj")
    public R createObj(@RequestParam(name = "count") Integer count,
                       @RequestParam(name = "size", required = false, defaultValue = "1024000") Integer size) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<BigObject> bigObjectList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
                bigObjectList.add(new BigObject(size));
        }

        MapUtils mapUtils = new MapUtils();
        mapUtils.put("count", count)
                .put("itemSize", size)
                .put("totalSize", count * size)
                .put("timecost", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return R.ok().put("data", mapUtils);
    }

    @GetMapping("/gc")
    public R invokeGc() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        //启用了参数DisableExplicitGC后下行代码失效
        System.gc(); //触发full gc
        return R.ok().put("gc-timecost", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @GetMapping("/direct-memory")
    public R testAllocateDirectMemory(@RequestParam(name = "count") Integer count) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        int i = 0;
        while (i++ < count)
        {
            ByteBuffer.allocateDirect(10 * 1024 * 1024);
        }
        return R.ok().put("gc-timecost", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @GetMapping("/jit")
    public R testJitFunc(@RequestParam(name = "count") Integer count) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        jitFuncInvoker(count);
        return R.ok().put("gc-timecost", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    @Async
    @GetMapping("/endless-loop")
    public void endLessLoop() {
        List<Object> dataList = new ArrayList<>(1024);
        while (true) {
            dataList.add(new byte[1024000]);
        }
    }

    private void jitFuncInvoker(int count) {
        for (int i = 0; i < count; i++) {
            jitFunc();
        }
    }

    private void jitFunc() {
        log.info("testJitFunc");
    }
}
