package com.quasar.backend.modules.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.quasar.backend.modules.base.enums.AuditResultType;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 审核日志
 *
 * @author Logan
 * @date 2020-06-13 18:59:50
 */
@Data
@TableName("audit_log")
public class AuditLogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 文章ID
     */
    private Long docId;
    /**
     * 文章标题
     */
    private String docTitle;
    /**
     * 审核结果
     */
    private AuditResultType resultType;
    /**
     * 操作人姓名
     */
    private String userName;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
