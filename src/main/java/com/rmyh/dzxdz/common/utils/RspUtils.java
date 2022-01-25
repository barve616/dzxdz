package com.rmyh.dzxdz.common.utils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.constant.CommonMessageCode;
import com.rmyh.dzxdz.common.enums.BizStateEnum;
import com.rmyh.dzxdz.domain.dto.PubRspDTO;
import com.rmyh.dzxdz.service.DataProcessService;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 处理请求响应信息类
 *
 * @author ck
 * created at 2022/1/24 11:09
 */
@Slf4j
public class RspUtils {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 交易成功响应
     * @param jsonData  响应业务数据Json字符串
     * @param dataProcessService    数据处理组件
     * @return  JSONObject
     */
    public static JSONObject successRspJson(String jsonData, DataProcessService dataProcessService){
        PubRspDTO pubRspDTO = new PubRspDTO();
        if(!StringUtils.isEmpty(jsonData)) {
            //业务数据不为空时加密/加签
            try {
                String encryptedData = dataProcessService.AESEncode(jsonData);
                pubRspDTO.setEncryptedData(encryptedData);
                pubRspDTO.setSignatureValue(dataProcessService.RSASign(jsonData));
            } catch (Exception e) {
                log.error("业务数据不为空时加密/加签异常:", e);
            }
        }
        pubRspDTO.setRspTime(simpleDateFormat.format(new Date()));
        pubRspDTO.setRspCode(CommonMessageCode.SUCC.getMsgCod());
        pubRspDTO.setBizState(BizStateEnum.SUCC.getValue());
        pubRspDTO.setRspMsg(CommonMessageCode.SUCC.getMsgInf());
        return JsonUtils.rspJson(pubRspDTO);
    }


    /**
     * 交易失败响应
     * @param rspCode   响应消息码
     * @param rspMsg    响应信息
     * @return  JSONObject
     */
    public static JSONObject failRspJson(String rspCode, String rspMsg){
        PubRspDTO pubRspDTO = new PubRspDTO();
        pubRspDTO.setRspTime(simpleDateFormat.format(new Date()));
        pubRspDTO.setRspCode(rspCode);
        pubRspDTO.setBizState(BizStateEnum.FAIL.getValue());
        pubRspDTO.setRspMsg(rspMsg);
        return JsonUtils.rspJson(pubRspDTO);
    }
}
