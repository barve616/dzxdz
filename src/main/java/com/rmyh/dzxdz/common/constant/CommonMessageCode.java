package com.rmyh.dzxdz.common.constant;


/**
 * @author zhang_jf
 * 消息码
 */
public enum CommonMessageCode  {

	SUCC("000000", "交易成功"),
	FAIL("000001", "数据异常"),
	VER_FAIL("000002", "验签失败"),
	SAVE_FAIL("000003", "保存失败"),
	;

	private String msgCod;
	private String msgInf;

	CommonMessageCode(String msgCod, String msgInf) {
		this.msgCod = msgCod;
		this.msgInf = msgInf;
	}

	public String getMsgCod() {
		return msgCod;
	}

	public String getMsgInf() {
		return msgInf;
	}
}
