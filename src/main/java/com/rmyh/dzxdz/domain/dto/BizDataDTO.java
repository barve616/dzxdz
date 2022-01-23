package com.rmyh.dzxdz.domain.dto;

import lombok.Data;

import java.io.Serializable;
/**
 * 数据处理结果信息
 *
 *
 *
 * @author zhang_jf
 * @since 2022-01-20
 */
@Data
public class BizDataDTO implements Serializable {
    private static final long serialVersionUID = 8444639425402170169L;
    /**
     *明文
     */
    private String plainData;
    /**
     *签名结果
     */
    private Boolean signResult;
}
