package com.circleback.exercise.email.signature.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for SigCaptureHeaderAddress
 */
public class SigCaptureHeaderAddress {

	private String name;

	@JsonProperty(required = true)
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
