package com.quasar.backend.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author Logan
 */
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
//        setFieldValByName("createTime", new Date(), metaObject);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
