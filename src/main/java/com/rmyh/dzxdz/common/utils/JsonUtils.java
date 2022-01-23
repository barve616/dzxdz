package com.rmyh.dzxdz.common.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
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
}
