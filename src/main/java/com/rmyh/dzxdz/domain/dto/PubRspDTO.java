package com.rmyh.dzxdz.domain.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * 通讯返回信息
 *
 *
 *
 * @author zhang_jf
 * @since 2022-01-20
 */
@Data
public class PubRspDTO implements Serializable {
    private static final long serialVersionUID = 5133309495512323882L;
    /**
     *返回访问状态字
     * S-成功
     * F-失败
     */
    private String bizState;
    /**
     *返回码
     */
    private String rspCode;
    /**
     *逻辑异常返回信息
     */
    private String rspMsg;
    /**
     *签名值
     */
    private String signatureValue;
    /**
     *加密值
     */
    private String encryptedData;
    /**
     *响应时间
     */
    private String rspTime;
}
