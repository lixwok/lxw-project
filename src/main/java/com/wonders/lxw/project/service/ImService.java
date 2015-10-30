package com.wonders.lxw.project.service;

import com.wonders.lxw.project.dto.im.IMClientCertificate;
import com.wonders.lxw.project.dto.im.IMToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

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
            ResponseEntity entity = requestIMChat(null, false, certificate, "POST", "token", IMToken.class);
            IMToken imToken = (IMToken) entity.getBody();
            redisTemplate.opsForValue().set("lxw:im:token", imToken.getAccess_token(), imToken.getExpires_in(), TimeUnit.DAYS);
        }
        return token;
    }


    /**
     * 获取环信接口的公共类
     *
     * @param headers
     * @param isNeedToken
     * @param requestBody
     * @param method
     * @param key
     * @param responseType
     * @return
     */
    public ResponseEntity requestIMChat(HttpHeaders headers, boolean isNeedToken, Object requestBody, String method, String key, Class<?> responseType) {
        if (null == headers) {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
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
