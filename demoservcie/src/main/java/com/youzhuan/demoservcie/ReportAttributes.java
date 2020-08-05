package com.youzhuan.demoservcie;


import com.alibaba.fastjson.JSONObject;

/**
 * @author 樱花满地集于我心
 * Create By 2020-08-05
 */
public class ReportAttributes {

    private String applianceId;
    private JSONObject attributes;

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public JSONObject getAttributes() {
        return attributes;
    }

    public void setAttributes(JSONObject attributes) {
        this.attributes = attributes;
    }

    public void put(String key,String value){
        if (attributes == null){
            attributes = new JSONObject();
        }
        attributes.put(key,value);
    }


}
