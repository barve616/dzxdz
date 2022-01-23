package com.rmyh.dzxdz.domain.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * 通讯请求信息
 *
 *
 *
 * @author zhang_jf
 * @since 2022-01-20
 */
@Data
public class PubReqDTO implements Serializable {
    private static final long serialVersionUID = 7321003107681058094L;
    /**
     *APIkey
     */
    private String apiKey;
    /**
     *签名值
     */
    private String signatureValue;
    /**
     *加密值
     */
    private String encryptedData;
    /**
     *请求时间
     */
    private String reqTime;
}
