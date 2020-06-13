package com.quasar.backend.modules.base.controller;

import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.R;
import com.quasar.backend.common.validator.ValidatorUtils;
import com.quasar.backend.modules.base.entity.AuditLogEntity;
import com.quasar.backend.modules.base.service.AuditLogService;
import com.quasar.backend.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

/**
 * 审核日志
 *
 * @author Logan
 * @date 2020-06-13 18:59:50
 */
@RestController
@RequestMapping("base/auditlog")
public class AuditLogController extends AbstractController {
    @Autowired
    private AuditLogService auditLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("base:auditlog:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = auditLogService.queryPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("base:auditlog:info")
    public R info(@PathVariable("id") Long id) {
        AuditLogEntity auditLog = auditLogService.getById(id);

        return R.ok().put("data", auditLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("base:auditlog:save")
    public R save(@RequestBody AuditLogEntity auditLog) {
        auditLog.setCreateTime(LocalDateTime.now());
        auditLog.setUserName(getUserName());
        auditLogService.save(auditLog);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("base:auditlog:update")
    public R update(@RequestBody AuditLogEntity auditLog) {
        ValidatorUtils.validateEntity(auditLog);
        auditLogService.updateById(auditLog);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("base:auditlog:delete")
    public R delete(@RequestBody Long[] ids) {
        auditLogService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
