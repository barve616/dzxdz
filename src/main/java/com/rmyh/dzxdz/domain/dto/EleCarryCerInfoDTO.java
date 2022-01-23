package com.rmyh.dzxdz.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 电子携带证信息
 * 
 *
 *
 * @author zhang_jf
 * @since 2022-01-20
 */
@Data
public class EleCarryCerInfoDTO implements Serializable {

    private static final long serialVersionUID = -4940223059481286519L;

    /**
     * 携带证编号
     */
    private String certificateNo;

    /**
     * 姓名
     */
    private String fullName;

    /**
     * 证件类别
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idNo;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 前往目的地
     */
    private String destination ;

    /**
     * 携带币种
     */
    private String carryCcy ;

    /**
     * 携带金额
     */
    private BigDecimal carryAmount ;

    /**
     * 折算美元金额
     */
    private BigDecimal dollarAmount ;

    /**
     * 签发日期
     */
    private String issueDate ;

    /**
     * 有效日期
     */
    private String effectiveDate ;

    /**
     * 备注
     */
    private String remark ;
}
