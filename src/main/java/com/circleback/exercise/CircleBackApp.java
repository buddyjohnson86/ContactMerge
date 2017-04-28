package com.circleback.exercise;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.circleback.exercise.email.signature.request.SigCaptureRequests;
import com.circleback.exercise.email.signature.response.SigCaptureContact;
import com.circleback.exercise.email.signature.response.SigCaptureResponse;
import com.circleback.exercise.rest.client.EmailCaptureRestClient;
import com.circleback.exercise.rest.client.config.ApplicationConfiguration;
import com.circleback.exercise.service.ContactMergeService;
import com.circleback.exercise.utility.SigCaptureFileUtilities;

/**
 * Main app for ContactMerge exercise
 */
@SpringBootApplication
public class CircleBackApp implements CommandLineRunner {

	/**
	 * Logger for class
	 */
	private static final Logger LOGGER = Logger.getLogger(CircleBackApp.class);

	@Autowired
	private ContactMergeService myContactMergeService;

	@Autowired
	private EmailCaptureRestClient myEmailCaptureRestClient;

	/**
	 * Main method for application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(CircleBackApp.class, args);
	}

	/**
	 * Run method initializes current set of contacts, send REST requests to
	 * extract contacts from emails, merges the contacts, then writes the final
	 * set of contacts to a file
	 */
	public void run(String... args) throws Exception {

		LOGGER.info("initializing contacts");
		
		// Initialize the current set of contacts
		myContactMergeService.initCaptureContacts(SigCaptureFileUtilities
				.convertJsonToSigCaptureContacts(ApplicationConfiguration.FILE_TO_INIT_CONTACTS));

		// Generate SigCaptureRequests to pass to the CircleBack service
		// Then merge the response with current set of contacts
		LOGGER.info("Generating SigCaptureRequests");
		SigCaptureRequests lSigCaptureRequests = SigCaptureFileUtilities
				.convertJsonToSigCaptureRequests(ApplicationConfiguration.FILE_TO_POST);
	
		if (lSigCaptureRequests != null) {
			LOGGER.info("Submitting REST request");
			SigCaptureResponse lResponse = myEmailCaptureRestClient.sendSigCaptureRequest(lSigCaptureRequests);
			
			LOGGER.info("Merging contacts");
			myContactMergeService.compareResponse(lResponse);

			// Get fully merged set of contacts
			List<SigCaptureContact> lCurrentContacts = myContactMergeService.getCurrentContacts();

			// write contacts to file
			LOGGER.info("Writing contacts to file");
			SigCaptureFileUtilities.writeJsonToFile(lCurrentContacts, ApplicationConfiguration.FILE_TO_WRITE);
		}
	}
}
