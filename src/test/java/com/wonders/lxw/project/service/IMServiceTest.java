package com.wonders.lxw.project.service;

import com.wonders.lxw.project.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lixuanwu on 15/10/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class IMServiceTest {

    @Autowired
    private IMService imService;

    @Test
    public void testRetrieveToken() throws Exception {
        System.out.println(imService.retrieveToken());
    }

    @Test
    public void testRequestIMChat() throws Exception {
        Map<String, String> userMap = new HashMap();
        userMap.put("username", "longshao11");
        userMap.put("password", "123456");
        ResponseEntity responseEntity = imService.requestIMChat(null, false, userMap, "POST", "users", String.class);
        System.out.println(responseEntity.getStatusCode());
    }
}