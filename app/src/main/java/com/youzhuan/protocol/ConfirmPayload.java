package com.youzhuan.protocol;

import com.alibaba.fastjson.JSONObject;
import com.youzhuaniot.entity.v2.Appliance;
import com.youzhuaniot.entity.v2.Attribute;

import java.util.ArrayList;
import java.util.List;

public class ConfirmPayload {
	private String applianceId;

	private List<Attribute> attributes;
	private List<Appliance> discoveredAppliances;
	private JSONObject temperature,mode,fanSpeed;


	public List<Appliance> getDiscoveredAppliances() {
		return discoveredAppliances;
	}

	public void setDiscoveredAppliances(List<Appliance> discoveredAppliances) {
		this.discoveredAppliances = discoveredAppliances;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(Attribute attribute){
		if(this.attributes == null){
			this.attributes = new ArrayList<>();
		}
		this.attributes.add(attribute);
	}

	public String getApplianceId() {
		return applianceId;
	}

	public void setApplianceId(String applianceId) {
		this.applianceId = applianceId;
	}

	public JSONObject getTemperature() {
		return temperature;
	}

	public void setTemperature(String key,Object value) {
		if(this.temperature == null){
			temperature = new JSONObject();
		}
		temperature.put(key,value);
	}

	public JSONObject getMode() {
		return mode;
	}

	public void setMode(String key,Object value) {
		if(this.mode == null){
			mode = new JSONObject();
		}
		mode.put(key,value);
	}
	public JSONObject getFanSpeed() {
		return fanSpeed;
	}

	public void setFanSpeed(String key,Object value) {
		if(this.fanSpeed == null){
			fanSpeed = new JSONObject();
		}
		fanSpeed.put(key,value);
	}
}
