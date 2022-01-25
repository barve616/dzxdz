package com.rmyh.dzxdz.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.domain.dto.PubReqDTO;
import com.rmyh.dzxdz.entity.EleCarryCerInfoDO;
import com.rmyh.dzxdz.repository.PushCertificateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClssName: PushCertificateService
 * @description: 推送电子携带证信息
 * @author: wyf
 * @create: 2022-01-24 11:08
 **/
@Slf4j
@Component
public class PushCertificateService {
    @Value("${aes.key}")
    private String aesKey;
    @Value("${rmyh.rsa.pubkey}")
    private String rhRsaPubKey;
    @Value("${rmyh.rsa.prikey}")
    private String rhRsaPriKey;
    @Value("${third.rsa.pubkey}")
    private String thirdRsaPubKey;
    @Value("${third.rsa.prikey}")
    private String thirdRsaPriKey;
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    private DataProcessService dataProcessService;
    @Autowired
    private PushCertificateMapper pushCertificateMapper;

    @Autowired
    private HttpService httpService;

    /**
     * 处理待发送的数据
     *
     * @param data
     * @return JSONObject
     */
    public JSONObject querCert(String data)throws Exception {
        List<EleCarryCerInfoDO> eleCarryCerInfoDOS = pushCertificateMapper.selectList(null);

        JSONObject jsonObject = dataProcessService.handlePlainData(JSON.toJSONString(eleCarryCerInfoDOS));
        JSONObject rspJson = httpService.transport(jsonObject);
        return rspJson;
    }
}
