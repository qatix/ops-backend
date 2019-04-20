package com.quasar.backend.modules.base.controller;

import com.quasar.backend.common.utils.PageUtils;
import com.quasar.backend.common.utils.R;
import com.quasar.backend.common.validator.ValidatorUtils;
import com.quasar.backend.modules.base.entity.ProductEntity;
import com.quasar.backend.modules.base.service.ProductService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品
 *
 * @author Logan
 * @email hawk418@qq.com
 * @date 2018-10-13 11:11:14
 */
@RestController
@RequestMapping("base/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @RequiresPermissions("base:product:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = productService.queryPage(params);

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("base:product:info")
    public R info(@PathVariable("id") Integer id){
        ProductEntity product = productService.getById(id);

        return R.ok().put("data", product);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("base:product:save")
    public R save(@RequestBody ProductEntity product){
        productService.save(product);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @RequiresPermissions("base:product:update")
    public R update(@RequestBody ProductEntity product){
        ValidatorUtils.validateEntity(product);
        productService.updateById(product);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @RequiresPermissions("base:product:delete")
    public R delete(@RequestBody Integer[] ids){
        productService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
