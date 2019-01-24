package com.quasar.backend.common.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @Author: xionghui
 * @Date: 2018/8/17 下午2:47
 */
public class HttpUtil {
    public static String request(HttpMethod method, String url, HttpEntity requestEntity) {
        RestTemplate client = new RestTemplate();
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        String res = response.getBody();
        return res;
    }

    public static String getRedirectUri(HttpMethod method, String url, HttpEntity requestEntity) {
        RestTemplate client = new RestTemplate();
        //  执行HTTP请求
        ResponseEntity<String> response = client.exchange(url, method, requestEntity, String.class);
        URI location = response.getHeaders().getLocation();
        String uri = location.toString();
        return uri;
    }
}
