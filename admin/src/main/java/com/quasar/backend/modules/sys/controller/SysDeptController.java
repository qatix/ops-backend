package com.quasar.backend.modules.sys.controller;

import com.quasar.backend.common.utils.Constant;
import com.quasar.backend.common.utils.R;
import com.quasar.backend.modules.sys.entity.SysDeptEntity;
import com.quasar.backend.modules.sys.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 */
@Slf4j
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public R list() {
        log.info("get sys:dept:list");
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        return R.ok().put("data", deptList);
    }

    @RequestMapping("/tree")
    @RequiresPermissions("sys:dept:list")
    public R tree() {
        List<SysDeptEntity> deptList = sysDeptService.list();

        List<Map<String, Object>> resList = new ArrayList<>(deptList.size());
        for (SysDeptEntity dept : deptList) {
            Map<String, Object> deptMap = new HashMap<>(4);
            deptMap.put("id", dept.getId());
            deptMap.put("name", dept.getName());
            deptMap.put("pid", dept.getParentId());
            resList.add(deptMap);
        }
        return R.ok().put("data", resList);
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public R select() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());

        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return R.ok().put("data", deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public R info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDeptEntity sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }

                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;
        }

        return R.ok().put("data", deptId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public R info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.getById(deptId);

        return R.ok().put("data", dept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public R save(@RequestBody SysDeptEntity dept) {
        sysDeptService.save(dept);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public R update(@RequestBody SysDeptEntity dept) {
        sysDeptService.updateById(dept);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public R delete(@RequestParam(name = "id") Long deptId) {
        log.info("delete deptId:" + deptId);
        if (null == deptId) {
            return R.error("部门ID为空");
        } else {
            SysDeptEntity dept = sysDeptService.getById(deptId);
            if (null == dept) {
                return R.error("部门不存在");
            }
        }

        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return R.error("请先删除子部门");
        }

        sysDeptService.removeById(deptId);

        return R.ok();
    }

}
