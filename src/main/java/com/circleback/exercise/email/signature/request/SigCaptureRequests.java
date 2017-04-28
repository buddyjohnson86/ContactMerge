package com.circleback.exercise.email.signature.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for SigCaptureRequests
 */
public class SigCaptureRequests {

	@JsonProperty(required = true)
	private String request_id;

	@JsonProperty(required = true)
	private SigCaptureEmail[] messages;

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public SigCaptureEmail[] getMessages() {
		return messages;
	}

	public void setMessages(SigCaptureEmail[] messages) {
		this.messages = messages;
	}
}
