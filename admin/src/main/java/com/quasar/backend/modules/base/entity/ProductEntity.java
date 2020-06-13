package com.quasar.backend.modules.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 *
 * @author Logan
 *
 * @date 2018-10-13 11:11:14
 */
@Data
@TableName("n_product")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 编号
     */
    private String no;
    /**
     * 型号
     */
    private String model;
    /**
     * 描述
     */
    private String description;
    /**
     * 售价
     */
    private BigDecimal price;
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
