package com.wonders.lxw.project.controller.em;

import com.wonders.lxw.project.service.IMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
        headers.add("restrict-access", "true");

        final Map bodyMap = new HashMap();
        bodyMap.put("file", file.getBytes());
        LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<String, Object>() {{
            setAll((Map) bodyMap);
        }};
        ResponseEntity result = imService.requestIMChat(headers, true, linkedMultiValueMap, "POST", "chatfiles", String.class);

        return result.getStatusCode().toString();

    }
}
