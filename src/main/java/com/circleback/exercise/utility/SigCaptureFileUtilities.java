package com.circleback.exercise.utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.circleback.exercise.email.signature.request.SigCaptureRequests;
import com.circleback.exercise.email.signature.response.SigCaptureContact;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SigCaptureFileUtilities {

	/**
	 * Logger for class
	 */
	private static final Logger LOGGER = Logger.getLogger(SigCaptureFileUtilities.class);

	private static ObjectMapper MY_MAPPER = new ObjectMapper();

	/**
	 * Convert file to SigCaptureRequests to post to Circleback API
	 * 
	 * @param fileName
	 *            name of file to convert to SigCaptureRequests
	 * @return SigCaptureRequests
	 */
	public static SigCaptureRequests convertJsonToSigCaptureRequests(String fileName) {

		SigCaptureRequests lSigCaptureRequests = null;

		try {
			lSigCaptureRequests = MY_MAPPER.readValue(new File(fileName), SigCaptureRequests.class);
		} catch (JsonParseException e) {
			LOGGER.info("Parse exception: ", e);
		} catch (JsonMappingException e) {
			LOGGER.info("Json Mapping exception: ", e);
		} catch (IOException e) {
			LOGGER.info("IO Exception: ", e);
		}

		return lSigCaptureRequests;
	}

	/**
	 * Convert file to list of SigCaptureContacts.
	 * 
	 * @param fileName
	 *            file to convert to list of SigCaptureContacts
	 * @return list of SigCaptureContacts
	 */
	public static List<SigCaptureContact> convertJsonToSigCaptureContacts(String fileName) {
		List<SigCaptureContact> lSigCaptureContacts = new ArrayList<SigCaptureContact>();

		try {
			lSigCaptureContacts = MY_MAPPER.readValue(new File(fileName), MY_MAPPER.getTypeFactory()
					.constructCollectionType(List.class, SigCaptureContact.class));
		} catch (JsonParseException e) {
			LOGGER.info("Parse exception: ", e);
		} catch (JsonMappingException e) {
			LOGGER.info("Json Mapping exception: ", e);
		} catch (IOException e) {
			LOGGER.info("IO exception: ", e);
		}

		return lSigCaptureContacts;
	}

	/**
	 * Write merged list of SigCaptureContacts to a file
	 * 
	 * @param sigCaptureContacts
	 *            list of SigCaptureContacts
	 * @param fileName
	 *            file to write through
	 */
	public static File writeJsonToFile(List<SigCaptureContact> sigCaptureContacts, String fileName) {
		File lFile = null;
		
		try {
			lFile = new File(fileName);
			MY_MAPPER.writerWithDefaultPrettyPrinter().writeValue(lFile, sigCaptureContacts);
		} catch (JsonGenerationException e) {
			LOGGER.info("Json Generation exception: ", e);
		} catch (JsonMappingException e) {
			LOGGER.info("Json Mapping exception: ", e);
		} catch (IOException e) {
			LOGGER.info("IO exception: ", e);
		}
		
		return lFile;
	}

}
