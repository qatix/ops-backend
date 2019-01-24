package com.quasar.backend.controller;

import com.quasar.backend.service.SysGeneratorService;
import com.quasar.backend.utils.GenUtils;
import com.quasar.backend.utils.PageUtils;
import com.quasar.backend.utils.Query;
import com.quasar.backend.utils.R;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午9:12:58
 */
@Controller
@RequestMapping("/sys/generator")
public class SysGeneratorController {
    @Autowired
    private SysGeneratorService sysGeneratorService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<Map<String, Object>> list = sysGeneratorService.queryList(query);
        int total = sysGeneratorService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    @ResponseBody
    @RequestMapping("/get_config")
    public R getConfig() {
        Configuration config = GenUtils.getConfig();
        return R.ok().put("module", config.getString("moduleName"));
    }

    /**
     * 生成代码
     */
    @RequestMapping("/code")
    public void code(String tables, String module, HttpServletResponse response) throws IOException {
        byte[] data = sysGeneratorService.generatorCode(tables.split(","), module);

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-hhmmss");
        String fileName = String.format("code-gen-%s-%s.zip", module, format.format(new Date()));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
