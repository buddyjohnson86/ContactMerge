package com.circleback.exercise.rest.client;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.circleback.exercise.email.signature.request.SigCaptureRequests;
import com.circleback.exercise.email.signature.response.SigCaptureResponse;
import com.circleback.exercise.rest.client.config.ApplicationConfiguration;

/**
 * REST client for using Email Capture webservice
 */
@Service
public class EmailCaptureRestClient {

	/**
	 * HTTP headers
	 */
	private HttpHeaders myHttpHeaders;

	/**
	 * Constructor for REST client. Initializes HTTP Headers
	 */
	public EmailCaptureRestClient() {
		myHttpHeaders = new HttpHeaders();
		myHttpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		myHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
		myHttpHeaders.setCacheControl(ApplicationConfiguration.CACHE_CONTROL);
		myHttpHeaders.add(ApplicationConfiguration.API_KEY, ApplicationConfiguration.AUTHORIZATION_KEY);
	}

	/**
	 * Sends REST request with SigCaptureRequest
	 * 
	 * @param sigCaptureRequests
	 *            request contains Emails to be analyzed for contact information
	 * @return SigCaptureResponse which contains captured contacts
	 */
	public SigCaptureResponse sendSigCaptureRequest(SigCaptureRequests sigCaptureRequests) {
		RestTemplate lRestTemplate = new RestTemplate();
		HttpEntity<SigCaptureRequests> lHttpEntity = new HttpEntity<SigCaptureRequests>(sigCaptureRequests,
				myHttpHeaders);
		ResponseEntity<SigCaptureResponse> lResponseEntity = lRestTemplate.exchange(
				ApplicationConfiguration.getEmailCaptureUrl(), HttpMethod.POST, lHttpEntity, SigCaptureResponse.class);

		return lResponseEntity.getBody();
	}
}
