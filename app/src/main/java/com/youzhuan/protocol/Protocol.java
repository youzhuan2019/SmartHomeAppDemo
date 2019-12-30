package com.youzhuan.protocol;

import com.alibaba.fastjson.JSON;

public class Protocol {

	private Header header;

	private Object payload;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getPayload() {
		return JSON.toJSON(payload);
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}
}
