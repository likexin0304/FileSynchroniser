package kcl.paramount.group.entity;

import com.alibaba.fastjson.JSONObject;

import java.io.File;

public class Files {
    private String url;
    private String time;
    private String size;
    private String type;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        File file = new File(url);
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        this.setType(name.substring(pos + 1, name.length()));
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        String result = null;
        JSONObject jb = new JSONObject();
        jb.put("url", url);
        if (this.getTime() != null) {
            jb.put("time", time);
        }
        else {
            jb.put("time", "null");
        }
        if (this.getSize() != null) {
            jb.put("size", size);
        }
        else {
            jb.put("size", "null");
        }
        jb.put("type", type);
        return jb.toJSONString();
    }
}
