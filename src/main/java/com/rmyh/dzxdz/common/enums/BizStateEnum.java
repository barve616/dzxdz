package com.rmyh.dzxdz.common.enums;

/**
 * 状态枚举值
 *
 * @author zhang_jf
 * @version 1.0.0
 */
public enum BizStateEnum {

	/**
	 * 返回状态
	 */
	SUCC("S", "成功"),
	FAIL("F", "失败"),
		;

	private String value;
	private String desc;

	BizStateEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}


	public String getValue() {
		return value;
	}


	public String getDesc() {
		return desc;
	}
}
