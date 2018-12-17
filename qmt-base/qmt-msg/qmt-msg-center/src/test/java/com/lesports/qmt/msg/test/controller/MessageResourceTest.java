package com.lesports.qmt.msg.test.controller;

import com.lesports.qmt.msg.MsgCenterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URI;

/**
 * Created by zhangdeqiang on 2016/11/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MsgCenterApplication.class)
public class MessageResourceTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String baseUri = "http://localhost:8080";

    @Test
    public void testRead() {
        HttpHeaders headers = new HttpHeaders();
        RequestEntity requestEntity =
                new RequestEntity(headers, HttpMethod.POST, URI.create(baseUri + "/message/hello"));

        ResponseEntity responseEntity =
                restTemplate.exchange(requestEntity, String.class);

        System.out.println(responseEntity.getBody());
    }

    @Test
    public void testWrite() {
        HttpHeaders headers = new HttpHeaders();
        RequestEntity requestEntity =
                new RequestEntity("hello", headers, HttpMethod.POST, URI.create(baseUri + "/proto/write"));

        ResponseEntity responseEntity =
                restTemplate.exchange(requestEntity, String.class);
        System.out.println(responseEntity.getBody());
    }
}
