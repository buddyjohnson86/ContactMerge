package com.circleback.exercise.email.signature.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO for SigCaptureEmail
 */
public class SigCaptureEmail {

	@JsonProperty(required = true)
	private String user_id;

	@JsonProperty(required = true)
	private String message_id;

	@JsonProperty(required = true)
	private String sent_date;

	@JsonProperty(required = true)
	private SigCaptureHeaderAddress from;

	@JsonProperty(required = true)
	private SigCaptureHeaderAddress[] to;

	private SigCaptureHeaderAddress[] cc;

	private SigCaptureHeaderAddress[] bcc;

	@JsonProperty(required = true)
	private String content_type;

	@JsonProperty(required = true)
	private String body;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMessage_Id() {
		return message_id;
	}

	public void setMessage_Id(String message_Id) {
		this.message_id = message_Id;
	}

	public String getSent_date() {
		return sent_date;
	}

	public void setSent_date(String sent_date) {
		this.sent_date = sent_date;
	}

	public SigCaptureHeaderAddress getFrom() {
		return from;
	}

	public void setFrom(SigCaptureHeaderAddress from) {
		this.from = from;
	}

	public SigCaptureHeaderAddress[] getTo() {
		return to;
	}

	public void setTo(SigCaptureHeaderAddress[] to) {
		this.to = to;
	}

	public SigCaptureHeaderAddress[] getCc() {
		return cc;
	}

	public void setCc(SigCaptureHeaderAddress[] cc) {
		this.cc = cc;
	}

	public SigCaptureHeaderAddress[] getBcc() {
		return bcc;
	}

	public void setBcc(SigCaptureHeaderAddress[] bcc) {
		this.bcc = bcc;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
