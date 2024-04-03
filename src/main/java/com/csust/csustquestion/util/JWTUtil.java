package com.csust.csustquestion.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JWTUtil {

    private static final String key = "csust_question";

    private static final Logger LOG = LoggerFactory.getLogger(JWTUtil.class);


    public static String createToken(String userId, String authority){
        Map<String, Object> payLoad = new HashMap<>();
        DateTime now = DateTime.now();
        payLoad.put(JWTPayload.ISSUED_AT,now);//签发时间
        payLoad.put(JWTPayload.EXPIRES_AT,now.offsetNew(DateField.HOUR,24));
        payLoad.put(JWTPayload.NOT_BEFORE,now);//过期时间
        payLoad.put("userId",userId);
        payLoad.put("authority",authority);
        String token = cn.hutool.jwt.JWTUtil.createToken(payLoad, key.getBytes());//就使用默认的签名算法
        LOG.info("生成token{}",token);
        return token;
    }

    /**
     * 校验token
     * @param token
     * @return
     */
    public static boolean validate(String token){
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token).setKey(key.getBytes());
        try {
            boolean validate = jwt.validate(0);//容忍的过期时间
        }catch (Exception e){
            LOG.warn("token无效");
            return false;
        }
        LOG.info("token有效：{}",token);
        return true;
    }

    public static JSONObject getJSONObject(String token){
        JWT jwt = cn.hutool.jwt.JWTUtil.parseToken(token).setKey(key.getBytes());
        JSONObject payloads = jwt.getPayloads();
        payloads.remove(JWTPayload.NOT_BEFORE);
        payloads.remove(JWTPayload.EXPIRES_AT);
        payloads.remove(JWTPayload.ISSUED_AT);
        LOG.info("根据token获取的内容为：{}",payloads);
        return payloads;
    }

}
