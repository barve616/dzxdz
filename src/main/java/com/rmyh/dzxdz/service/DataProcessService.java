package com.rmyh.dzxdz.service;

import com.alibaba.fastjson.JSONObject;
import com.rmyh.dzxdz.common.constant.CommonMessageCode;
import com.rmyh.dzxdz.common.enums.BizStateEnum;
import com.rmyh.dzxdz.common.utils.JsonUtils;
import com.rmyh.dzxdz.common.utils.SecurityUtils;
import com.rmyh.dzxdz.domain.dto.BizDataDTO;
import com.rmyh.dzxdz.domain.dto.PubReqDTO;
import com.rmyh.dzxdz.domain.dto.PubRspDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据处理组件，比如加解密，加验签
 *
 * @author zhang_jf
 * @version 1.0.0
 * created  at 2022/1/20 11:13
 */
@Slf4j
@Component
public class DataProcessService {
    @Value("${aes.key}")
    private  String aesKey;
    @Value("${rmyh.rsa.pubkey}")
    private  String rhRsaPubKey;
    @Value("${rmyh.rsa.prikey}")
    private  String rhRsaPriKey;
    @Value("${third.rsa.pubkey}")
    private  String thirdRsaPubKey;
    @Value("${third.rsa.prikey}")
    private  String thirdRsaPriKey;
    @Value("${api.key}")
    private String apiKey;
    /**
     * AES解密
     *
     * @param encryptedData
     * @return 明文
     */
    public String AESDecode(String encryptedData) throws Exception {
        return SecurityUtils.AESDecode(encryptedData, aesKey);
    }

    /**
     * AES加密
     *
     * @param plainData
     * @return 密文
     */
    public String AESEncode(String plainData) throws Exception{
        return SecurityUtils.AESEncode(plainData, aesKey);
    }

    /**
     * RSA验签
     *
     * @param plainData,signatureValue
     * @return 验签结果
     */
    public Boolean RSAVerify(String plainData, String signatureValue) throws Exception{
        return SecurityUtils.RSAVerify(plainData, signatureValue, rhRsaPubKey);
    }

    /**
     * RSA加签
     *
     * @param plainData
     * @return 签名串
     */
    public String RSASign(String plainData) throws Exception{
        return SecurityUtils.RSASign(plainData, rhRsaPriKey);
    }

    /**
     * 处理收到的数据
     *
     * @param pubReqDTO
     * @return BizDataDTO
     */
    public BizDataDTO handle(PubReqDTO pubReqDTO) throws Exception{
        String encryptedData = pubReqDTO.getEncryptedData();
        String signatureValue = pubReqDTO.getSignatureValue();


        //加密


        //加签








        log.info("待解密的数据:{}", encryptedData);
        String plainData = AESDecode(encryptedData);
        log.info("解密后的数据:{}", plainData);
        Boolean signResult = RSAVerify(plainData, signatureValue);
        log.info("验签结果:{}", (signResult == true)?"通过":"不通过");
        BizDataDTO bizDataDTO = new BizDataDTO();
        bizDataDTO.setPlainData(plainData);
        bizDataDTO.setSignResult(signResult);

        return bizDataDTO;
    }

    /**
     * 处理待发送的数据
     *
     * @param plainData
     * @return JSONObject
     */
    public JSONObject handlePlainData(String plainData) throws Exception{
        log.info("待加密的数据:{}", plainData);
        String encryptedData = AESEncode(plainData);
        String signatureValue = RSASign(plainData);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        PubReqDTO pubReqDTO = new PubReqDTO();
        pubReqDTO.setReqTime(simpleDateFormat.format(new Date()));
        pubReqDTO.setApiKey(apiKey);
        pubReqDTO.setEncryptedData(encryptedData);
        pubReqDTO.setSignatureValue(signatureValue);
        JSONObject reqJson = (JSONObject)JSONObject.toJSON(pubReqDTO);
        log.info("待发送的数据:{}", reqJson.toJSONString());
        return reqJson;
    }

}
