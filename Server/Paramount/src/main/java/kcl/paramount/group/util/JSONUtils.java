package kcl.paramount.group.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
    public static String getJSONString(String status, String info) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("info", info);
        return jsonObject.toJSONString();
    }
}
