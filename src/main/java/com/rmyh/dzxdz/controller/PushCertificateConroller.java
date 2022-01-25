package com.rmyh.dzxdz.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.utils.RspUtils;
import com.rmyh.dzxdz.service.HttpService;
import com.rmyh.dzxdz.service.PushCertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClssName: PushCertificateConroller
 * @description: 推送电子携带证信息
 * @author: wyf
 * @create: 2022-01-24 10:08
 **/
@RestController
@Slf4j
public class PushCertificateConroller {
    @Autowired
    PushCertificateService pushCertificateServices;



    @Autowired
    private HttpService httpService;

    @RequestMapping(value="/push/certificate")
    public JSONObject pushCertificate(@RequestBody(required =false)String data) throws Exception {
        if (!StringUtils.isEmpty(data)){
            log.info("为空",data);
        }{
            log.info("不为空",data);
        }
        JSONObject jsonObject = pushCertificateServices.querCert(data);

        JSONObject rspJson= httpService.transport(jsonObject);
        return  rspJson;

    }
}
