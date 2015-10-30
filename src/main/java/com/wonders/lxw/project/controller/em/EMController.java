package com.wonders.lxw.project.controller.em;

import com.wonders.lxw.project.dto.result.ControllerResult;
import com.wonders.lxw.project.service.IMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * Created by lixuanwu on 15/10/28.
 */
@RestController
@RequestMapping("em")
public class EMController {

    @Autowired
    private IMService imService;

    @RequestMapping(value = "sendFiles", method = RequestMethod.POST)
    public Object sendFiles(@Param("file") MultipartFile file) throws IOException {

        //返回的类型
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>() {{
            add(MediaType.APPLICATION_JSON);
        }};

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(acceptableMediaTypes);
        headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add("restrict-access", "true");

        Map bodyMap = new HashMap();
        bodyMap.put("file", file.getBytes());
        LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<String, Object>() {{
//            setAll(bodyMap);
        }};
        linkedMultiValueMap.setAll(bodyMap);

        ResponseEntity result = imService.requestIMChat(headers, true, linkedMultiValueMap, "POST", "chatfiles", String.class);
        return result.getStatusCode().toString();

    }

    @RequestMapping("registerUser")
    public Object registerUser(@RequestParam final String username, @RequestParam final String password) {
        HashMap useInfo = new HashMap() {
            {
                put("username", username);
                put("password", password);
            }
        };
        ResponseEntity result = imService.requestIMChat(null, true, useInfo, "POST", "users", String.class);
        if (result.getStatusCode() == HttpStatus.OK) {
            return new ControllerResult().setRet_code(0).setMessage("").setRet_values("注册功能");
        }
        return new ControllerResult().setRet_code(-1).setMessage("").setRet_values("注册失败");


    }
}
