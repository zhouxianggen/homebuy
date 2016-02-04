package com.hg.www.selller.data.define;

import org.json.JSONObject;

/**
 * json 格式的可序列化数据
 */
public interface JsonSerializable {
    public Object parse(String str);
    public Object parse(JSONObject jsonObject);
    public String serialize();
}
