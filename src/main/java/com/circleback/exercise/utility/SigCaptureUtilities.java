package com.circleback.exercise.utility;

import org.springframework.util.StringUtils;

public class SigCaptureUtilities {

	/**
	 * Checks if phone numbers are equals. Checks to see that they are both not
	 * empty and that when you strip the special characters out, the remaining
	 * digits match.
	 * 
	 * @param phoneNumber1
	 *            1st phone number to compare
	 * @param phoneNumber2
	 *            2nd phone number to compare
	 * @return true if the non special characters match up
	 */
	public static boolean arePhoneNumbersEqual(String phoneNumber1, String phoneNumber2) {
		boolean lResult = false;
		if (!(StringUtils.isEmpty(phoneNumber1) && StringUtils.isEmpty(phoneNumber2))) {
			if (stripPhoneNumber(phoneNumber1).equals(stripPhoneNumber(phoneNumber2))) {
				lResult = true;
			}
		}

		return lResult;
	}

	/**
	 * Remove special characters from phone number so you are comparing just the
	 * digits. This is to ensure that something like 609-212-3233 matches
	 * (609)212-3233
	 * 
	 * @param phoneNumber
	 *            Phone Number to be modified
	 * @return Phone number without special characters
	 */
	public static String stripPhoneNumber(String phoneNumber) {
		String lResult = "";
		if (phoneNumber != null) {
			lResult = phoneNumber;
			if (lResult.startsWith("1-") || lResult.startsWith("+1") || lResult.startsWith("1(")) {
				lResult = lResult.replaceFirst("1", "");
			}
			lResult = lResult.replaceAll("[^a-zA-Z0-9]", "");
		}

		return lResult;
	}

}
