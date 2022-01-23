package com.rmyh.dzxdz.service;

import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
/**
 * 数据发送组件
 *
 * @author zhang_jf
 * @version 1.0.0
 * created  at 2022/1/20 16:31
 */
@Slf4j
@Component
public class HttpService {
    @Value("${third.req.url}")
    private String reqUrl;
    @Value("${third.req.connect.timeout}")
    private Integer conectTimeout;
    @Value("${third.req.read.timeout}")
    private Integer readTimeout;

    public JSONObject transport(final JSONObject reqJson) throws RestClientException {
        JSONObject rspJson = null;
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(conectTimeout * 1000);
        requestFactory.setReadTimeout(readTimeout * 1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType(CommonConstants.MEDIA_TYPE);
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(reqJson.toString(), headers);
        ResponseEntity<String> resp = restTemplate.postForEntity(reqUrl, formEntity, String.class);
        if (resp.getStatusCode().is2xxSuccessful()){
            rspJson = JSONObject.parseObject(resp.getBody());
        }
        return rspJson;
    }
}
