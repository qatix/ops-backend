package com.quasar.backend.modules.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quasar.backend.modules.base.enums.PhoneType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 机型信息
 *
 * @author Logan
 *
 * @date 2018-08-27 18:02:48
 */
@Data
@Accessors(chain = true)
@TableName("n_phone")
public class PhoneEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 机型代码
     */
    private String code;
    /**
     * 类型
     */
    private PhoneType type;
    /**
     * 描述
     */
    private String description;
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
