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
    	String pubKeyStr ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh7u+HXib8YRx3dxYyZIjs5ZwMvC+Gp3OmCHuzoixfp1MJrDc4ZKYJwGTsJWyneCOqRIwKkWQUK64pLqD6kj2t704oWAr8cG1/fzwPXnkhLyBvkjFXbXfXjQrQUGqcd1mBoMCNfCYegjM4aReJS4gQXZixXdAE1CgD1i0/KeBN8SEHOEscsLlnPihYv3+qK9eYjz/wdEX+lf7K8jGz5GI8BV7n3S7VTQ8U49NFNMLcbGrSOIq+enah8LQWBGi/s06nNPVeJAEO9Z2id6bWVsvCutpFsMvRJCgl4c8vm5j+0eTjl2jj0725gbEjZ9W5gUNBC9H58JHDnRHW0ix028BDwIDAQAB";
    	String priKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCHu74deJvxhHHd3FjJkiOzlnAy8L4anc6YIe7OiLF+nUwmsNzhkpgnAZOwlbKd4I6pEjAqRZBQrrikuoPqSPa3vTihYCvxwbX9/PA9eeSEvIG+SMVdtd9eNCtBQapx3WYGgwI18Jh6CMzhpF4lLiBBdmLFd0ATUKAPWLT8p4E3xIQc4SxywuWc+KFi/f6or15iPP/B0Rf6V/sryMbPkYjwFXufdLtVNDxTj00U0wtxsatI4ir56dqHwtBYEaL+zTqc09V4kAQ71naJ3ptZWy8K62kWwy9EkKCXhzy+bmP7R5OOXaOPTvbmBsSNn1bmBQ0EL0fnwkcOdEdbSLHTbwEPAgMBAAECggEAKD3LIaWuZi5kK+i2EhzXpNjHLvMdCXsb0xLMYEM6jFVKTHZHJkF2Bqp7BPF84T3ATfPsf7vcC2+hooUBsyYqr1LURVPj82sA7+pbt3459/iDZszgZuYS8TT6VgCH7fY711ZkA2Xvbs7JH2TB3LgOLBpTMkQmpRNUQPiFwBpRPxz/kByWdAJ74myI8hYrEodjVlXmDQnqnCBzjyA5BmVFLFtU/bKo5fXlk9RPGzDv2SK8ylBiGIkp0Pr0aI95WQOakAzGPr0JPBwPGQANamFuVINGJXFyB15frJByrwQAZ+fA9kALUM0KefgFia7ksr+Tap9OrjSDgZHKPv2C0Mc1cQKBgQDAW+GN4m9Q74D7jOuSh2x+KrdQF8JEpUu3lqHxuDGAeK8gEejuWKKB+sOLt6xJxjwCWxBBOXhAMxvAfv8g2pJKQpEI+8gEeCvXt6X6QFGFW0cAD5Zi8k4nn/tdOPYkFU7bzVeD3xULrC7ftvwV/B5tVCd+t2i/1ET18ekFjUATEwKBgQC0o+Dwey4+J8HBT+eB9zmuXUsTky+IIDn8tDju/HeEeqkjwRff8354rwtT9goSq/qaBt1D1W3NvVXAMfttFKTtndwoYTpB4+cveWc2XttUEoryTquvZ08N+DQbm9UrvbSCjSAjUE3MCha4oBXgGtTBcM4In0WO13T3VWciAbJdlQKBgQCX5HW4+gJ4YIbzG5WxAOq0vbo+rmQinHurnnCGVLISBjoTEtFh0++Ov5rFquWK49Pi8FFnj5/a4gCaAi4nnTBDH0kzVM3eaZ4FGukfMMaujSMFc0mHHFqGg5eTRzVlzMWWXxEp+m+95dqloPJx0cGdBwSg1OcMAuvpYXplfeIIrQKBgBHw2M1pno0jy7WUZwzcbgkoUOWPz7+4W6oCiEkMIB6zRAs+CU3/1AbOc9SR8iwqkogruFwdXXt6m4KsNCmATGUhMZV9Gpkitnpbl2mqACSYdt5b++c+3nYnuZ0oXAKtI7l3pMJ9Hkzuj5UkqSeJ4qmXt47ZqsHmGkLaY5TYEEwFAoGBAL4BP/we+pxRX1ACaBGiuECmC4D7WrajNXJ7jxThEPgtuv7uyLSSmUJz55V0oB5Ayydpsn90pfpfN8aEUGHZ6/ejvKGfJTopciBZcHzhb8Ib1XOGHaQtHwdOuAleINrGUl/7yCT2MxijUt8cXwE+rAvswbb/9/Fuu2/tTYGsVsjG";
    	String a= "admin:password";
    	
    	//RSA加密/解密测试
//    	String rsaEpt = MD5withRSAEncrypt(a,pubKeyStr);
//    	System.out.println("=======rsaEpt: "+rsaEpt);
//    	String ss = MD5withRSADecrypt(rsaEpt,priKeyStr);
//    	System.out.println("=======ss: "+ss);
    	
    	//RSA签名/验签
    	String rsaSign = RSASign(a,priKeyStr);
    	System.out.println("=======rsaSign: "+rsaSign);
    	boolean rsaVfy = RSAVerify(a, rsaSign, pubKeyStr);
    	System.out.println("=======rsaVfy: "+rsaVfy);
    	
    	//AES加密/解密测试
//    	System.out.println(genAesKey(128).length());
//    	System.out.println(genAesKey(128));
//    	String key = "AF93C090CD0D2FD4578EF4FED103372C";
//    	String encodeStr = AESEncode("test",key);
//    	System.out.println(encodeStr);
//    	//d7482e704a5f192d66beca0a0114fab0
//    	String decodeStr = AESDecode(encodeStr,key);
//    	System.out.println(decodeStr);
    	
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
