package kcl.paramount.group.util;

import com.alibaba.fastjson.JSONObject;

/*
    create the format of response message
    [status: ;info: ]
    status: success or fail
    info: return message about request
 */
public class JSONUtils {
    public static String getJSONString(String status, String info) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        jsonObject.put("info", info);
        return jsonObject.toJSONString();
    }
}
