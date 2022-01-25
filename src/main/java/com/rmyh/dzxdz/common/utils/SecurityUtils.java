package com.rmyh.dzxdz.common.utils;

import com.rmyh.dzxdz.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SecurityUtils {

    /**
     * AES解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String AESDecode(String data, String key) throws Exception {
        byte[] bData = Base64.getDecoder().decode(data.getBytes(CommonConstants.CHARTSET));
        SecretKey aeskey = new SecretKeySpec(parseHexStr2Byte(key), CommonConstants.ENCRYPT_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CommonConstants.AES_MODE);
        cipher.init(Cipher.DECRYPT_MODE, aeskey);
        byte[] bResult = cipher.doFinal(bData);
        return new String(bResult, CommonConstants.CHARTSET);
    }

    /**
     * AES加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String AESEncode(String data, String key) throws Exception {
        SecretKey aeskey = new SecretKeySpec(parseHexStr2Byte(key), CommonConstants.ENCRYPT_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CommonConstants.AES_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, aeskey);
        byte[] bResult = cipher.doFinal(data.getBytes(CommonConstants.CHARTSET));
        String sData = new String(Base64.getEncoder().encode(bResult));
        return sData;
    }

//    /**
//     * RSA公钥加密
//     *
//     * @param data
//     * @param sPubkey
//     * @return
//     * @throws Exception
//     */
//    public static String MD5withRSAEncrypt(String data, String sPubkey) throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(sPubkey);
//        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PublicKey publicKey = keyFactory.generatePublic(keySpec);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        byte[] bResult = cipher.doFinal(data.getBytes(CHARTSET));
//        return new String(Base64.getEncoder().encode(bResult));
//    }
//
//    /**
//     * RSA私钥解密
//     *
//     * @param data
//     * @param sPriKey
//     * @return
//     * @throws Exception
//     */
//    public static String MD5withRSADecrypt(String data, String sPriKey) throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(sPriKey);
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        byte[] bResult = cipher.doFinal(Base64.getDecoder().decode(data));
//        return new String(bResult, CHARTSET);
//    }

    /**
     * 加签函数
     * @param data 加签报文
     * @param privateKey RSA私钥
     * @return
     */
    public static String RSASign(String data, String privateKey) throws Exception{
        byte keyBytes[] = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(CommonConstants.SIGN_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(CommonConstants.SIGNATURE);
        signature.initSign(priKey);
        signature.update(data.getBytes(CommonConstants.CHARTSET));
        return new String(Base64.getEncoder().encode(signature.sign()));
    }

    /**
     * 验签函数
     * @param data 验签报文
     * @param sign 签名
     * @param public_key RSA公钥
     * @return
     */
    public static boolean RSAVerify(String data, String sign, String public_key)throws Exception{
        byte publicKey[] = Base64.getDecoder().decode(public_key);
        byte decodeSign[] = Base64.getDecoder().decode(sign);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(CommonConstants.SIGN_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(CommonConstants.SIGNATURE);
        signature.initVerify(pubKey);
        signature.update(data.getBytes(CommonConstants.CHARTSET));
        return signature.verify(decodeSign);
    }

    /**
     * 生成AES秘钥
     *
     * @return
     * @throws Exception
     */
    public static String genAesKey(int lenth) throws Exception {

    	KeyGenerator kgen = KeyGenerator.getInstance(CommonConstants.ENCRYPT_ALGORITHM);
    	kgen.init(lenth);
    	SecretKey sKey = kgen.generateKey();

    	return new String(parseByte2HexStr(sKey.getEncoded()));

    }




    /**
     * 生成公私钥对
     *
     * @return
     * @throws Exception
     */
    public static Map<String, String> genRsaKey() throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(CommonConstants.SIGN_ALGORITHM);
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("PaymentIcbc".getBytes());
        keygen.initialize(2048);
        KeyPair keys = keygen.genKeyPair();
        RSAPublicKey pubkey = (RSAPublicKey) keys.getPublic();
        RSAPrivateKey prikey = (RSAPrivateKey) keys.getPrivate();
        Map<String, String> keyMap = new HashMap<String, String>();
        String pubKeyStr = new String(Base64.getEncoder().encode(pubkey.getEncoded()));
        String priKeyStr = new String(Base64.getEncoder().encode(prikey.getEncoded()));

        log.info("=======pubKeyStr:{}", pubKeyStr);

        log.info("=======priKeyStr:{}", priKeyStr);

        keyMap.put("RSAPUBKEY", pubKeyStr);
        keyMap.put("RSAPRIKEY", priKeyStr);
        return keyMap;
    }

    public static void main(String[] args) throws Exception {
//    	genRsaKey();
        //人行公钥
    	String pubKeyStr ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoJlYZa+8twSIhINp/7yJQiNyv2L9YmOy2nusskEESzKP8Axcvq2vyn4k4I1ZRMS20XZyn0JJztXs1l3a7k2M2cPIu4fpK2e8H11NDEZcPcKpM+BUADJado6qr2ci0dmC1ZIJoHgeqhE2uWNnb26gQbHBwxzqjYXjge5OBFQWf1EAQs9+r/uUnPxxuAedQeBW03NYCR46Kwi0kZSy5jW1fvuBCbiFmuZY+5i183rTGnIZjoYWylcic5lf+M0zx9FaAT0HnF7/5Afnfyr914ojiNLxb0rGv29lyWQTk4Ht1UKio2EwR1eryat7S7MLYYntBRZ/A/sydX7QJk4P6PcruwIDAQAB";
    	//人行私钥
    	String priKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgmVhlr7y3BIiEg2n/vIlCI3K/Yv1iY7Lae6yyQQRLMo/wDFy+ra/KfiTgjVlExLbRdnKfQknO1ezWXdruTYzZw8i7h+krZ7wfXU0MRlw9wqkz4FQAMlp2jqqvZyLR2YLVkgmgeB6qETa5Y2dvbqBBscHDHOqNheOB7k4EVBZ/UQBCz36v+5Sc/HG4B51B4FbTc1gJHjorCLSRlLLmNbV++4EJuIWa5lj7mLXzetMachmOhhbKVyJzmV/4zTPH0VoBPQecXv/kB+d/Kv3XiiOI0vFvSsa/b2XJZBOTge3VQqKjYTBHV6vJq3tLswthie0FFn8D+zJ1ftAmTg/o9yu7AgMBAAECggEATtU3KSMstYIKKVIAtEIt9kNETmIfEy7xWoIe8ZksrGTzdtf6prbHddSZ8Ygw66rMOuGzvi1h4Z/tFAUaNKPoofSTEkrS0DWpiJPaAE6BdOea+xYA5d/RvCSn8FXmJ7BnosJ+60BLfRvaCwKagX6CN/0zqb0F/dZdKSh7L3C/Ylrqc4ogKPtMmJdg5l/pCMQXA9fEWJXe/GGIZu0nfQDuXtSmDQgjF2ecT35GvkpTves1MMTWcGWfgCasQ4+zjT52E6B/zKYHZaPE3DIemkqPp4YwFyk0X73CiEvoHbfJPRoq5xTYgxVlc4H5kmRuMAzmlUiUm99s0KtmA389DUO6cQKBgQD92fhd+urV4HiUD5IK/06lOq3f0e1E66P8KIWkgEFbqsVMjrEpVpe2RC7Px7W7zx+zBA4hnUQwdoJpKB8v7QBDA0u+w60GVu8M2LjBoLGS6Ej+cyoVnVQRMwRjFZ+6NL8vdk4a8VxLQwNDyBHlc1UmHg/L4DUEVdtqqMKtlHoBrwKBgQCh9VJIrZbmVbu7rjbWW1s/rKzlBtagFQGoB5yvPRB6fUYAhOkm49qdP6lBsjsiuDUz6f4t00M7ylV+HEtYqSDtVy0FEcDjIQGIs6oHRrRTbnhZiIYDbKtpKmZe4VseoKEuD65DIAuTIUOrnU0UAYDAlSHF1jfHd9scals8ScF1tQKBgQCyE3RNyTVv79XmV73lhRAQjMdomYVOsdcJjwuhSG+Q9I0PlZHmI96td8s26ywrobLlC9U183LgZGI7jm/CWQmz+N3r5qy8I8PySR3ihu3K1rRN+0/sdb+BiT5sm8C/CT2M9/r2n5ZVHZ9ury0Ovwdeg/wk0xWeAvS3L8fRqLSl9QKBgFYx2OQDNt9JW4uMMXUt08d0n+OYGxvBcks6GeKNrjpyd5IJKojfFVdBLVRgheZ5gR8q9nLvD8Y/bfabGmKKSJLoJXagNzbqNwXJEMGkmI4aqgSEi0ZBTIzA2xC+AR+wv3djMwxYfrCEMJ6iLgZqslpMRFglJTSMjll2JS5V1pdNAoGBALHlxi1slOw1w1QNUujOB43zYj0LywuX1hiw49DIJZD4r+UNMlTWvM5AIj7y69ZuQJWQE0vYCAdxMaf5N5NgXtx2pjZSJZKezJzmzsGBEn9U76jTNPw+/tzPahpCa5UZsaaFNNu/+SmCrdJeKIFo05xCubtYThZz+HudBxDOnXht";
//    	String a= "admin:password";

    	//RSA加密/解密测试
//    	String rsaEpt = MD5withRSAEncrypt(a,pubKeyStr);
//    	System.out.println("=======rsaEpt: "+rsaEpt);
//    	String ss = MD5withRSADecrypt(rsaEpt,priKeyStr);
//    	System.out.println("=======ss: "+ss);

    	//RSA签名/验签
//    	String rsaSign = RSASign(a,priKeyStr);
//    	System.out.println("=======rsaSign: "+rsaSign);
//    	boolean rsaVfy = RSAVerify(a, rsaSign, pubKeyStr);
//    	System.out.println("=======rsaVfy: "+rsaVfy);

    	//AES加密/解密测试
//    	System.out.println(genAesKey(128).length());
//    	System.out.println(genAesKey(128));
//    	String key = "AF93C090CD0D2FD4578EF4FED103372C";
//    	String encodeStr = AESEncode("test",key);
//    	System.out.println(encodeStr);
//    	//d7482e704a5f192d66beca0a0114fab0
//    	String decodeStr = AESDecode(encodeStr,key);
//    	System.out.println(decodeStr);
        //推送出境信息
//        String str = "{ \"certificateNo\":\"NO0000001\", \"carryCcy\":\"USD\", \"carryAmount\":\"100\", \"dollarAmount\":\"100\", \"departureDate\":\"20220117\", \"isNormal\":\"0\" }";
        //查询电子携带证信息（以证件号形式）
//        String str = "{\"idType\":\"01\",\"idNo\":\"44334333433332\"}";
        String key = "15240A5E7FCF31E618E93D60CC2B8ED6";
        String str="{\"certificateNo\":\"NO0000001\"}";

        String encodeStr = AESEncode(str, key);
        System.out.println("=====encodeStr: " + encodeStr);
        String decodeStr = AESDecode(encodeStr,key);
    	System.out.println("=====decodeStr: " + decodeStr);

        //RSA签名
        String rsaSign = RSASign(str,priKeyStr);
        System.out.println("=======rsaSign: "+rsaSign);
        //验签
        boolean rsaVfy = RSAVerify(str, rsaSign, pubKeyStr);
        System.out.println("=======rsaVfy: "+rsaVfy);
    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                    String hex = Integer.toHexString(buf[i] & 0xFF);
                    if (hex.length() == 1) {
                            hex = '0' + hex;
                    }
                    sb.append(hex.toUpperCase());
            }
            return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1)
                    return null;
            byte[] result = new byte[hexStr.length()/2];
            for (int i = 0;i< hexStr.length()/2; i++) {
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                    result[i] = (byte) (high * 16 + low);
            }
            return result;
    }

}
