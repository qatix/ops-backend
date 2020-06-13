package com.quasar.backend.modules.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 国家
 *
 * @author Logan
 *
 * @date 2018-08-23 21:10:23
 */
@Data
@TableName("n_country")
public class CountryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    /**
     * 国家代码
     */
    private String code;
    /**
     * 英文名
     */
    private String enName;
    /**
     * 中文名
     */
    private String zhName;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
