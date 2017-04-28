package com.circleback.exercise.email.signature.response;

public class SigCaptureResult {

	private String user_id;
	
	private String message_id;
	
	private SigCaptureContact[] contacts;
	
	private String[] signature_blocks;
	
	private String result_code;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public SigCaptureContact[] getContacts() {
		return contacts;
	}

	public void setContacts(SigCaptureContact[] contacts) {
		this.contacts = contacts;
	}

	public String[] getSignature_blocks() {
		return signature_blocks;
	}

	public void setSignature_blocks(String[] signature_blocks) {
		this.signature_blocks = signature_blocks;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	
	
}
