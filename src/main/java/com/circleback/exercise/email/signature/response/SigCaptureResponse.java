package com.circleback.exercise.email.signature.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SigCaptureResponse {

	@JsonProperty(required = true)
	private String request_id;
	
	@JsonProperty(required = true)
	private String contact_count;
	
	@JsonProperty(required = true)
	private SigCaptureResult[] results;

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getContact_count() {
		return contact_count;
	}

	public void setContact_count(String contact_count) {
		this.contact_count = contact_count;
	}

	public SigCaptureResult[] getResults() {
		return results;
	}

	public void setResults(SigCaptureResult[] results) {
		this.results = results;
	}	
}
