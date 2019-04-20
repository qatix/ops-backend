package com.quasar.backend.modules.base.controller;

import com.quasar.backend.common.utils.R;
import com.quasar.backend.datasources.DsEnum;
import com.quasar.backend.datasources.annotation.DataSource;
import com.quasar.backend.modules.base.entity.CountryEntity;
import com.quasar.backend.modules.base.entity.PhoneEntity;
import com.quasar.backend.modules.base.service.CountryService;
import com.quasar.backend.modules.base.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: Logan
 * @Date: 2018/8/30 3:23 PM
 */
@RestController
@RequestMapping("/base/api")
public class CommonController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private PhoneService phoneService;

    /**
     * 获取全部国家
     *
     * @return
     */
    @RequestMapping("/country_list")
    public R getCountryList() {
        List<CountryEntity> countryEntityList = countryService.list();
        List resList = countryEntityList.stream().map(item -> {
            Map<String, Object> channel = new HashMap<>();
            channel.put("id", item.getCode());
            channel.put("name", item.getEnName());
            return channel;
        }).collect(Collectors.toList());

        return R.ok().put("data", resList);
    }

    /**
     * 获取全部机型
     *
     * @return
     */
    @RequestMapping("/model_list")
    public R getModelList() {
        List<PhoneEntity> phoneEntityList = phoneService.list();
        List resList = phoneEntityList.stream().map(item -> {
            Map<String, Object> channel = new HashMap<>();
            String code = item.getCode().replace('_', ' ');
            channel.put("id", code);
            channel.put("name", code);
            return channel;
        }).collect(Collectors.toList());

        return R.ok().put("data", resList);
    }

    /**
     * 使用第二个数据源
     * @return
     */
    @DataSource(name = DsEnum.TERMINAL)
    @RequestMapping("/model_list2")
    public R getModelList2() {
        List<PhoneEntity> phoneEntityList = phoneService.list();
        List resList = phoneEntityList.stream().map(item -> {
            Map<String, Object> channel = new HashMap<>();
            String code = item.getCode().replace('_', ' ');
            channel.put("id2", code);
            channel.put("name2", code);
            return channel;
        }).collect(Collectors.toList());

        return R.ok().put("data", resList);
    }
}
