package com.rmyh.dzxdz.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhang_jf
 * @since 2022-01-20
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("电子携带证_主表")
public class EleCarryCerInfoDO implements Serializable {

    private static final long serialVersionUID = -5446779197243485199L;

    /**
     * 携带证编号
     */
    @TableField(value = "携带证编号")
    private String certificateNo;

    /**
     * 姓名
     */
    @TableField(value = "姓名")
    private String fullName;

    /**
     * 证件类别
     */
    @TableField(value = "证件类别")
    private String idType;

    /**
     * 证件号码
     */
    @TableField(value = "证件号码")
    private String idNo;

    /**
     * 国籍
     */
    @TableField(value = "国籍")
    private String nationality;

    /**
     * 前往目的地
     */
    @TableField(value = "前往目的地")
    private String destination ;

    /**
     * 携带币种
     */
    @TableField(value = "携带币种")
    private String carryCcy ;

    /**
     * 携带金额
     */
    @TableField(value = "携带金额")
    private BigDecimal carryAmount ;

    /**
     * 折算美元金额
     */
    @TableField(value = "折算美元金额")
    private BigDecimal dollarAmount ;

    /**
     * 签发日期
     */
    @TableField(value = "签发日期")
    private String issueDate ;

    /**
     * 有效日期
     */
    @TableField(value = "有效日期")
    private String effectiveDate ;

    /**
     * 备注
     */
    @TableField(value = "备注")
    private String remark ;
}
