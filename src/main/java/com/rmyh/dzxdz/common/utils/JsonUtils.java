package com.rmyh.dzxdz.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /**
     *转换为json对象
     *
     * @param object
     * @return JSONObject
     */
    public static JSONObject rspJson(Object object){
        JSONObject resjsonObject = (JSONObject)JSONObject.toJSON(object);
        log.info("返回报文:{}", resjsonObject.toJSONString());
        return resjsonObject;
    }

    /**
     * Json字符串转换对象
     * @param jsonStr json字符串
     * @param clazz 转换的类
     * @return
     */
    public static Object objectFromJson(String jsonStr, Class clazz){
        JsonParser localJsonParser = null;
        Object localObject1 = null;
        try {
            localJsonParser = objectMapper.getJsonFactory().createJsonParser(jsonStr);
            localObject1 = localJsonParser.readValueAs(clazz);
        } catch (IOException e) {
            log.error("objectFromJson error:", e);
        } finally {
            if(localJsonParser != null){
                try {
                    localJsonParser.close();
                } catch (IOException e) {
                    log.error("localJsonParser close error:", e);
                }
            }
        }
        return localObject1;
    }
}
