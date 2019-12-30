package com.youzhuan.protocol;

import com.youzhuaniot.entity.v2.Appliance;
import java.util.List;

public class Payload {

	private String accessToken;
	private String openUid;
	private Appliance appliance;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getOpenUid() {
		return openUid;
	}

	public void setOpenUid(String openUid) {
		this.openUid = openUid;
	}


	public Appliance getAppliance() {
		return appliance;
	}

	public void setAppliance(Appliance appliance) {
		this.appliance = appliance;
	}



}
