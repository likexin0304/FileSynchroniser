package kcl.paramount.group.entity;

import com.alibaba.fastjson.JSONObject;

public class Dirs {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        JSONObject jb = new JSONObject();
        jb.put("url", url);
        return jb.toJSONString();
    }
}
