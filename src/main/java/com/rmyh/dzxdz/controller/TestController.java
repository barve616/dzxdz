package com.rmyh.dzxdz.controller;

import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.constant.CommonMessageCode;
import com.rmyh.dzxdz.common.enums.BizStateEnum;
import com.rmyh.dzxdz.common.utils.JsonUtils;
import com.rmyh.dzxdz.domain.dto.PubRspDTO;
import com.rmyh.dzxdz.entity.EleCarryCerInfoDO;
import com.rmyh.dzxdz.repository.EleCarryCerInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 测试程序
 *
 * @author zhang_jf
 * created at 2022/1/19 15:09
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private EleCarryCerInfoMapper eleCarryCerInfoMapper;

    @RequestMapping(value="/gateway/test")
    public JSONObject json(@RequestBody JSONObject requestJson) {
        log.info("接收到的报文:{}", requestJson.toJSONString());

        //测试登记中文表
        insert();
        PubRspDTO pubRspDTO = new PubRspDTO();
        pubRspDTO.setRspCode(CommonMessageCode.SUCC.getMsgCod());
        pubRspDTO.setBizState(BizStateEnum.SUCC.getValue());
        pubRspDTO.setRspMsg(CommonMessageCode.SUCC.getMsgInf());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        pubRspDTO.setRspTime(simpleDateFormat.format(new Date()));
        return JsonUtils.rspJson(pubRspDTO);
    }

    private void insert(){
        EleCarryCerInfoDO eleCarryCerInfoDO = new EleCarryCerInfoDO();
        eleCarryCerInfoDO.setCarryCcy("USD");
        eleCarryCerInfoDO.setCarryAmount(new BigDecimal(5.33));
        eleCarryCerInfoDO.setDollarAmount(new BigDecimal(5.33));
        eleCarryCerInfoDO.setCertificateNo(UUID.randomUUID().toString().replaceAll("-", ""));
        eleCarryCerInfoDO.setDestination("France");
        eleCarryCerInfoDO.setEffectiveDate("20220220");
        eleCarryCerInfoDO.setFullName("张剑锋");
        eleCarryCerInfoDO.setIssueDate("20220120");
        eleCarryCerInfoDO.setNationality("Chinese");
        eleCarryCerInfoDO.setIdType("01");
        eleCarryCerInfoDO.setIdNo("440304");
        eleCarryCerInfoMapper.insert(eleCarryCerInfoDO);
    }

}
