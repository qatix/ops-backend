package com.quasar.backend.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quasar.backend.common.utils.R;
import com.quasar.backend.modules.sys.entity.SysDeptEntity;
import com.quasar.backend.modules.sys.entity.SysUserEntity;
import com.quasar.backend.modules.sys.service.SysDeptService;
import com.quasar.backend.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/sys/common")
public class SysCommonController extends AbstractController {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/base")
    public R getConfig() {
        Map<String, Object> map = new HashMap<>();
        map.put("depts", getAllDepts());
        map.put("users", getAllUsers());

        return R.ok().put("data", map);
    }

    private List getAllDepts() {
        List<SysDeptEntity> deptList = sysDeptService.selectList(null);

        List<Map<String, Object>> resList = new ArrayList<>(deptList.size());
        for (SysDeptEntity dept : deptList) {
            Map<String, Object> subMap = new HashMap<>(4);
            subMap.put("id", dept.getId());
            subMap.put("name", dept.getName());
            subMap.put("pid", dept.getParentId());
            resList.add(subMap);
        }
        return resList;
    }

    private List getAllUsers() {
        List<SysUserEntity> userList = sysUserService.selectList(
                new QueryWrapper<SysUserEntity>().eq("status", 1)
        );

        List<Map<String, Object>> resList = new ArrayList<>(userList.size());
        for (SysUserEntity user : userList) {
            Map<String, Object> subMap = new HashMap<>(4);
            subMap.put("id", user.getId());
            subMap.put("name", user.getUsername());
            subMap.put("email", user.getEmail());
            resList.add(subMap);
        }
        return resList;
    }

}
