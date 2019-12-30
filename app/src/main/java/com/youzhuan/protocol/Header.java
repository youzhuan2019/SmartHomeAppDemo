package com.youzhuan.protocol;

import java.util.UUID;

public class Header {
	public Header(){
		messageId = UUID.randomUUID().toString();
	}
	/**
	 * namespace : DuerOS.ConnectedHome.Discovery
	 * name : DiscoverAppliancesRequest
	 * messageId : 6d6d6e14-8aee-473e-8c24-0d31ff9c17a2
	 * payloadVersion : 1
	 */

	private String namespace;
	private String name;
	private String messageId;
	private String payloadVersion = "1";

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getPayloadVersion() {
		return payloadVersion;
	}

	public void setPayloadVersion(String payloadVersion) {
		this.payloadVersion = payloadVersion;
	}
}
