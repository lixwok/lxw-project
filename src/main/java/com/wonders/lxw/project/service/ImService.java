package com.wonders.lxw.project.service;

import com.wonders.lxw.project.dto.IMClientCertificate;
import com.wonders.lxw.project.dto.IMToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by lixuanwu on 15/10/23.
 */
@Service
public class IMService {

    private static final String IM_SER_URL = "http://a1.easemob.com/xlab/fullwayhealth/{key}";
    private static final String IM_APP_CLIENT_ID = "YXA6CZG4kHI7EeWNlskgVjXM1Q";
    private static final String IM_APP_CLIENT_SECRET = "YXA6Wau9qBt4ibvxi0DXxP9Y93_wXug";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取环信token
     *
     * @return
     */
    public String retrieveToken() {
        String token = redisTemplate.opsForValue().get("lxw:im:token");
        if (StringUtils.isEmpty(token)) {
            IMClientCertificate certificate = new IMClientCertificate(IM_APP_CLIENT_ID, IM_APP_CLIENT_SECRET);
            IMToken imToken = restTemplate.postForObject(IM_SER_URL, certificate, IMToken.class,"token");
            redisTemplate.opsForValue().set("lxw:im:token", imToken.getAccess_token(), imToken.getExpires_in(), TimeUnit.DAYS);
        }
        return token;
    }

    //    public ResponseEntity requestIMChat(Map<String, String> headerMap, boolean isNeedToken, Object requestBody, String method, String key, Class<?> responseType) {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//        if (null != headerMap) {
//            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//                System.out.println(entry.getKey() + entry.getValue());
//            }
//            headers.setAll(headerMap);
//        }
//        if (isNeedToken) {
//            headers.add("Authorization", "Bearer " + retrieveToken());
//        }
//        return restTemplate.exchange(
//                IM_SER_URL,
//                Enum.valueOf(HttpMethod.class, method),
//                new HttpEntity(requestBody, headers),
//                responseType,
//                key
//        );
//    }

    public ResponseEntity requestIMChat(HttpHeaders headers, boolean isNeedToken, Object requestBody, String method, String key, Class<?> responseType) {

        if (null == headers) {
            headers = new HttpHeaders();
            headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        }
        if (isNeedToken) {
            headers.add("Authorization", "Bearer " + retrieveToken());
        }
        return restTemplate.exchange(
                IM_SER_URL,
                Enum.valueOf(HttpMethod.class, method),
                new HttpEntity(requestBody, headers),
                responseType,
                key
        );
    }
}
