package com.quasar.backend.modules.base.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.R;
import com.quasar.backend.common.validator.ValidatorUtils;
import com.quasar.backend.modules.base.entity.CountryEntity;
import com.quasar.backend.modules.base.service.CountryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 国家
 *
 * @author Logan
 * @date 2018-08-23 21:10:23
 */
@RestController
@RequestMapping("base/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping("/all")
    public R allChannels(@RequestParam Map<String, Object> params) {
        List<CountryEntity> countryList = countryService.list(new QueryWrapper<CountryEntity>());

        return R.ok().put("data", countryList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("base:country:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = countryService.queryPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("base:country:info")
    public R info(@PathVariable("id") Integer id) {
        CountryEntity country = countryService.getById(id);

        return R.ok().put("data", country);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("base:country:save")
    public R save(@RequestBody CountryEntity country) {
        countryService.save(country);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("base:country:update")
    public R update(@RequestBody CountryEntity country) {
        ValidatorUtils.validateEntity(country);
        countryService.updateById(country);//全部更新

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("base:country:delete")
    public R delete(@RequestBody Integer[] ids) {
        countryService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
