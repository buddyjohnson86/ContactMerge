package com.circleback.exercise;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.circleback.exercise.email.signature.response.SigCaptureContact;
import com.circleback.exercise.rest.client.config.ApplicationConfiguration;
import com.circleback.exercise.service.ContactMergeService;
import com.circleback.exercise.utility.SigCaptureFileUtilities;

public class ContactMergeServiceUnitTest {

	private ContactMergeService myTestObject;
	

	@Before
	public void init() {
		myTestObject = new ContactMergeService();
	}
	
	@Test
	public void testInit() {
		List<SigCaptureContact> lExpectedContacts = SigCaptureFileUtilities.convertJsonToSigCaptureContacts(ApplicationConfiguration.FILE_TO_INIT_CONTACTS);
		myTestObject.initCaptureContacts(lExpectedContacts);
		
		List<SigCaptureContact> lActualContacts = myTestObject.getCurrentContacts();
		Assert.assertEquals(lExpectedContacts.size(), lActualContacts.size());
		
		for(SigCaptureContact lSigContact : lExpectedContacts) {
			Assert.assertTrue(lActualContacts.contains(lSigContact));
		}
	}
}
