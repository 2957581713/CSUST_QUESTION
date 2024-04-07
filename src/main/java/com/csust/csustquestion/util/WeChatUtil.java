package com.csust.csustquestion.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csust.csustquestion.constant.WXConstant;

import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {
  public static JSONObject getSessionKeyOrOpenId(String code) {
    String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
    Map<String, String> requestUrlParam = new HashMap<>();
    requestUrlParam.put("appid", WXConstant.APPID);
    requestUrlParam.put("secret", WXConstant.SECRET);
    requestUrlParam.put("js_code", code);
    requestUrlParam.put("grant_type", "authorization_code");
    JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doPost(requestUrl, requestUrlParam));
    return jsonObject;
  }
}
