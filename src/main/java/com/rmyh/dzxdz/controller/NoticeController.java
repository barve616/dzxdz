package com.rmyh.dzxdz.controller;

import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.constant.CommonMessageCode;
import com.rmyh.dzxdz.common.enums.BizStateEnum;
import com.rmyh.dzxdz.common.utils.JsonUtils;
import com.rmyh.dzxdz.domain.dto.BizDataDTO;
import com.rmyh.dzxdz.domain.dto.PubReqDTO;
import com.rmyh.dzxdz.domain.dto.PubRspDTO;
import com.rmyh.dzxdz.service.DataProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 接收海关推送的处境信息
 *
 * @author zhang_jf
 * created at 2022/1/20 15:09
 */
@RestController
@Slf4j
public class NoticeController {
    @Autowired
    private DataProcessService dataProcessService;

    @RequestMapping(value="/gateway/notice")
    public JSONObject json(@RequestBody JSONObject requestJson) {
        log.info("接收到的报文:{}", requestJson.toJSONString());

        PubReqDTO pubReqDTO = requestJson.toJavaObject(PubReqDTO.class);
        PubRspDTO pubRspDTO = new PubRspDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        pubRspDTO.setRspTime(simpleDateFormat.format(new Date()));

        BizDataDTO bizDataDTO;
        try {
            bizDataDTO = dataProcessService.handle(pubReqDTO);

        } catch (Exception e) {
            log.error("处理数据异常:", e);
            pubRspDTO.setRspCode(CommonMessageCode.FAIL.getMsgCod());
            pubRspDTO.setBizState(BizStateEnum.FAIL.getValue());
            pubRspDTO.setRspMsg(CommonMessageCode.FAIL.getMsgInf());
            return JsonUtils.rspJson(pubRspDTO);
        }
        if (!bizDataDTO.getSignResult()){
            pubRspDTO.setRspCode(CommonMessageCode.VER_FAIL.getMsgCod());
            pubRspDTO.setBizState(BizStateEnum.FAIL.getValue());
            pubRspDTO.setRspMsg(CommonMessageCode.VER_FAIL.getMsgInf());
            return JsonUtils.rspJson(pubRspDTO);
        }
        //业务处理TODO
        pubRspDTO.setBizState(BizStateEnum.SUCC.getValue());
        return JsonUtils.rspJson(pubRspDTO);
    }

}
