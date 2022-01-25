package com.rmyh.dzxdz.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.constant.CommonMessageCode;
import com.rmyh.dzxdz.common.utils.JsonUtils;
import com.rmyh.dzxdz.common.utils.RspUtils;
import com.rmyh.dzxdz.domain.dto.BizDataDTO;
import com.rmyh.dzxdz.domain.dto.EleCarryCerInfoDTO;
import com.rmyh.dzxdz.domain.dto.PubReqDTO;
import com.rmyh.dzxdz.entity.EleCarryCerInfoDO;
import com.rmyh.dzxdz.service.DataProcessService;
import com.rmyh.dzxdz.service.SearchCertificateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClssName: SearchCertificateConroller
 * @description: 查询电子携带证信息（以携带证编号形式)
 * @author: wyf
 * @create: 2022-01-24 10:12
 **/
@RestController
@Slf4j
public class SearchCertificateConroller {
    @Autowired
    private DataProcessService dataProcessService;
    @Autowired
    SearchCertificateService searchCertificateService;
    @RequestMapping(value="/search/certificate")
    public JSONObject searchCertificate(@RequestBody JSONObject requestJson) throws Exception {
        log.info("接收到的报文:{}", requestJson.toJSONString());
        PubReqDTO pubReqDTO = requestJson.toJavaObject(PubReqDTO.class);

        BizDataDTO bizDataDTO;
        try {
            bizDataDTO = dataProcessService.handle(pubReqDTO);
        } catch (Exception e) {
            log.error("handle data error:", e);
            return RspUtils.failRspJson(CommonMessageCode.FAIL.getMsgCod(), CommonMessageCode.FAIL.getMsgInf());
        }
        if (!bizDataDTO.getSignResult()){
            return RspUtils.failRspJson(CommonMessageCode.VER_FAIL.getMsgCod(), CommonMessageCode.VER_FAIL.getMsgInf());
        }
        //获取解密后的数据
        String plainData = bizDataDTO.getPlainData();
        EleCarryCerInfoDTO eleCarryCerInfoDTO   = (EleCarryCerInfoDTO) JsonUtils.objectFromJson( plainData,EleCarryCerInfoDTO.class);
        if(StringUtils.isEmpty(eleCarryCerInfoDTO.getCertificateNo())){
            throw new Exception("certificateNo parameter is null");
        }
        //通过id查询
        EleCarryCerInfoDO  eleCarryCerInfoDO = searchCertificateService.selectById( eleCarryCerInfoDTO.getCertificateNo());
        log.info("通过id查询数据--："+ JSON.toJSON(eleCarryCerInfoDO));
        //加密返回
        JSONObject jsonObject = RspUtils.successRspJson(JSON.toJSONString(eleCarryCerInfoDO), dataProcessService);
        return jsonObject;

    }

}
