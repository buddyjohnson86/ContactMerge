package com.circleback.exercise;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.circleback.exercise.email.signature.request.SigCaptureRequests;
import com.circleback.exercise.email.signature.response.SigCaptureContact;
import com.circleback.exercise.rest.client.config.ApplicationConfiguration;
import com.circleback.exercise.utility.SigCaptureFileUtilities;

public class SigCaptureFileUtilitiesUnitTest {

	@Test
	public void testConvertJsonToSigCaptureRequests() {
		SigCaptureRequests lSigCaptureRequests = SigCaptureFileUtilities
				.convertJsonToSigCaptureRequests(ApplicationConfiguration.FILE_TO_POST);

		Assert.assertEquals(5, lSigCaptureRequests.getMessages().length);
	}

	@Test
	public void testConvertJsonToSigCaptureContacts() {
		List<SigCaptureContact> lSigCaptureContacts = SigCaptureFileUtilities
				.convertJsonToSigCaptureContacts(ApplicationConfiguration.FILE_TO_INIT_CONTACTS);

		Assert.assertEquals(8, lSigCaptureContacts.size());
	}

	/**
	 * Read a file and convert to list of SigCaptureContacts. Write the list to
	 * a file and then convert that new file back to a list of
	 * SigCaptureContacts and compare against the original list. If they match,
	 * that verifies that the file was written to successfully.
	 */
	@Test
	public void testWriteJsonToFile() {
		List<SigCaptureContact> lSigCaptureContacts1 = SigCaptureFileUtilities
				.convertJsonToSigCaptureContacts(ApplicationConfiguration.FILE_TO_INIT_CONTACTS);

		String lTestFile = "src/main/resources/test";

		File lFile = SigCaptureFileUtilities.writeJsonToFile(lSigCaptureContacts1, lTestFile);

		List<SigCaptureContact> lSigCaptureContacts2 = SigCaptureFileUtilities
				.convertJsonToSigCaptureContacts(lTestFile);

		Assert.assertEquals(lSigCaptureContacts1, lSigCaptureContacts2);
		
		lFile.delete();
	}
}
