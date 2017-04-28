package com.circleback.exercise;

import org.junit.Assert;
import org.junit.Test;

import com.circleback.exercise.utility.SigCaptureUtilities;

public class SigCaptureUtilitiesUnitTest {

	@Test
	public void testStripPhoneNumbers() {
		Assert.assertEquals("2323432342", SigCaptureUtilities.stripPhoneNumber("1-232-343-2342"));
	}
}
