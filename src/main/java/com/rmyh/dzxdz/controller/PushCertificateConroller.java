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
 * 定时调用，如果附言字段是空的就需要发给海关，发送成功就更新附言为已发送海关
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
    public JSONObject pushCertificate(@RequestBody(required =false)String remark) throws Exception {
        if (!StringUtils.isEmpty(remark)){
            //发送给海关并更新附言字段为"已发送"

            log.info("为空",remark);
        }{
            log.info("不为空",remark);
        }
        JSONObject jsonObject = pushCertificateServices.querCert(remark);

        JSONObject rspJson= httpService.transport(jsonObject);
        return  rspJson;

    }
}
