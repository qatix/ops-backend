package com.quasar.backend.test;

import com.quasar.backend.entity.UserEntity;
import org.springframework.beans.BeanUtils;

public class BeanToMap {
    public static void main(String[] args) {
        UserEntity user = UserEntity.builder().userId(11L).mobile("13100001111").username("zhang").build();
//        BeanUtils.
    }
}
