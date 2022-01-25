package com.rmyh.dzxdz.common.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhang_jf
 * 公共变量
 */
public class CommonConstants {
	public final static String MEDIA_TYPE = "application/json; charset=UTF-8";
	public final static String AES_MODE = "AES/ECB/PKCS5Padding";
	public final static String CHARTSET = "UTF-8";
	public final static String ENCRYPT_ALGORITHM = "AES";
	public final static String SIGN_ALGORITHM = "RSA";
	public final static String SIGNATURE = "SHA256withRSA";

	/**
	 * 推送信息必输字段集合
	 */
	public final static List<String> PUSH_DATA_COLS = Arrays.asList("certificateNo", "carryCcy", "carryAmount", "departureDate", "isNormal");
}
