package com.quasar.backend.modules.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.R;
import com.quasar.backend.common.validator.ValidatorUtils;
import com.quasar.backend.modules.base.entity.PhoneEntity;
import com.quasar.backend.modules.base.service.PhoneService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 机型信息
 *
 * @author Logan
 * @date 2018-08-27 18:02:48
 */
@RestController
@RequestMapping("base/phone")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:phone:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = phoneService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @GetMapping("/all")
    public R getPhoneListByChannel(@RequestParam Map<String, Object> params) {
        String channel = (String) params.get("selectedChannel");
//
//        PhoneEntity phoneEntity =  new PhoneEntity();
//        phoneEntity.setChannel("chan").setCode("code-"+System.currentTimeMillis()).setType(PhoneType.FEATURE_PHONE).setDescription("desc");
//        System.out.println(phoneEntity.toString());
//        phoneService.save(phoneEntity);


        List<PhoneEntity> phoneList = null;
        phoneList = phoneService.list(
                new QueryWrapper<PhoneEntity>().eq("channel", channel));
        return R.ok().put("data", phoneList);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:phone:info")
    public R info(@PathVariable("id") Integer id) {
        PhoneEntity phone = phoneService.getById(id);

        return R.ok().put("data", phone);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:phone:save")
    public R save(@RequestBody PhoneEntity phone) {
        phoneService.save(phone);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:phone:update")
    public R update(@RequestBody PhoneEntity phone) {
        System.out.println("update:" + phone.toString());
        ValidatorUtils.validateEntity(phone);
        phoneService.updateById(phone);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:phone:delete")
    public R delete(@RequestBody Integer[] ids) {
        phoneService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
