package com.rmyh.dzxdz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rmyh.dzxdz.common.utils.SecurityUtils;
import com.rmyh.dzxdz.entity.EleCarryCerInfoDO;
import com.rmyh.dzxdz.repository.EleCarryCerInfoMapper;
import com.rmyh.dzxdz.service.DataProcessService;
import com.rmyh.dzxdz.service.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@SpringBootTest
@Slf4j

class DzxdzApplicationTests {
    @Autowired
    private DataProcessService dataProcessService;

    @Autowired
    private EleCarryCerInfoMapper eleCarryCerInfoMapper;
    @Autowired
    private HttpService httpService;
    private String data = "{\n" +
            "\t\"reimNo\":\"NO0000001\",\n" +
            "\t\"accountCode\":\"6222621310031088988\",\n" +
            "\t\"accountName\":\"交行客户\",\n" +
            "\t\"bankName\":\"交通银行深圳分行\",\n" +
            "\t\"tranferMoeny\":\"20.02\",\n" +
            "\t\"payAccountCode\":\"500015455585512\",\n" +
            "\t\"payAccountName\":\"张老*\",\n" +
            "\t\"payBankName\":\"招商银行\"\n" +
            "}\n";
    //生成AES密钥，默认使用128位
    @Test
    void genAesKey() throws Exception{
        String aesKey = SecurityUtils.genAesKey(128);
        log.info("生成的AES密钥:{}", aesKey);
    }

    //生成RSA公私钥对
    @Test
    void genRsaKey() throws Exception{
        SecurityUtils.genRsaKey();
    }

    //使用AES加密
    @Test
    void aesEncode() throws Exception{
        String aesStr = dataProcessService.AESEncode(data);
        log.info("加密后的数据：{}", aesStr);
    }

    //使用AES解密
    @Test
    void aesDecode() throws Exception{
        String aesStr = "jLpUSDyd/szROoFMUSLznjAAwPGg1GRc8MPCKWFw1ELfx6lY3afN6foWuHgzETK+0+E1Nk64Z5+G1UM0+5QMZBb6HIkmFhvQ0ULceDPZI+JpC7WzZO6v2IB56XuU799+qgdPDujhxGPmHsLOVoOXQycky5IsONobvchdV4w3T4v/CAgIWUy0u+E3uumE2TcxgUbD943ixLtHl+38Ir8yBb6p8Xe9d42Ch3dIOKoApk6R5lFF0mI33Pb3f7M+Cl7Fj2smtGCZ0QjdSj3s8sFzuEWWhEw2UVuVDvBcAPet54LJvkN5yH7OnU/9d2SbsrOEjymMakL+bX00zSkzZ5AeaQGaN3xC6GvS5BOdYlztDBw=";
        String data = dataProcessService.AESDecode(aesStr);
        log.info("解密后的数据:{}", data);
    }

    //使用RSA加签
    @Test
    void rsaSign() throws Exception{
        String signStr = dataProcessService.RSASign(data);
        log.info("签名串:{}", signStr);
    }

    //使用RSA验签
    @Test
    void rsaVerify() throws Exception{
        //签名串
        String signStr = "HMb5YnlMrVB/AjaKI2ZG2d3dcEov9LDsALzHy70TBiL+U33S2t3XYt1X3oSltzoE/DNedRQiST9JlVdazfXUAvA2vrGAVgVNzL8cG0I2+0pod2Q8KI934kPgfYef46iTiUBA5hUm3MH70UxQtxSIorS+BwkA2UoPdKgosAr1GMczrVlwZWTePCUeVRuGH0/tgcABoe5qlQYJqF4wjIVVRnEkl/nAP9keoCMRQEih3FdZkNGS0zD0RHLmvJPAyfTz0Ht5Sx9m1KgbMVoYT4hxk2mPfr+zd69nWh9Bpnwrn92fL0mEGbjpVFLFW/esFCi5LngeRf2ovf1LRHNhudirvA==";
        Boolean signResult = dataProcessService.RSAVerify(data, signStr);
        log.info("验签结果:{}", (signResult == true?"通过":"不通过"));
    }

    //客户端请求示例
    @Test
    void httpClient() throws Exception{
        JSONObject reqJson = dataProcessService.handlePlainData(data);

        JSONObject rspJson1 = httpService.transport(reqJson);
        log.info("返回数据:{}", rspJson1.toJSONString());
    }
    @Test
    void page() throws Exception{

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("携带证编号","6050422ba4584f44a63c20575facb4f0");
        Page<EleCarryCerInfoDO> eleCarryCerInfoDOPage = eleCarryCerInfoMapper.selectPage(new Page<>(1, 2), queryWrapper);
        System.out.println(JSON.toJSON(eleCarryCerInfoDOPage));



    }


}
