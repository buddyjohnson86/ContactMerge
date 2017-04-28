package com.circleback.exercise.email.signature.response;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.circleback.exercise.utility.SigCaptureUtilities;

public class SigCaptureContact {

	private String full_name;

	private String prefix;

	private String first_name;

	private String middle_name;

	private String last_name;

	private String suffix;

	private String nick_name;

	private String title;

	private String company;

	private String[] emails;

	private SigCapturePostalAddress[] addresses;

	private SigCapturePhoneNumber[] phone_numbers;

	private SigCaptureSocialProfile[] social_profiles;

	private String[] urls;

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public SigCapturePostalAddress[] getAddresses() {
		return addresses;
	}

	public void setAddresses(SigCapturePostalAddress[] addresses) {
		this.addresses = addresses;
	}

	public SigCapturePhoneNumber[] getPhone_numbers() {
		return phone_numbers;
	}

	public void setPhone_numbers(SigCapturePhoneNumber[] phone_numbers) {
		this.phone_numbers = phone_numbers;
	}

	public SigCaptureSocialProfile[] getSocial_profiles() {
		return social_profiles;
	}

	public void setSocial_profiles(SigCaptureSocialProfile[] social_profiles) {
		this.social_profiles = social_profiles;
	}

	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String[] urls) {
		this.urls = urls;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int lResult = 1;
		lResult = 31 * lResult + (StringUtils.isEmpty(first_name) ? 0 : first_name.hashCode());
		lResult = 31 * lResult + (StringUtils.isEmpty(last_name) ? 0 : last_name.hashCode());
		return lResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean lResult = false;

		// SigCaptureContacts are considered equal if the first/last name
		// matches and either the email or phone number matches. Since there can
		// be multiple emails and phone numbers per SigCaptureContact, only one
		// of the emails and/or phone numbers in each SigCaptureContact needs to
		// match
		if (object instanceof SigCaptureContact) {
			SigCaptureContact lSigContact = (SigCaptureContact) object;
			if (lSigContact.hashCode() == hashCode()) {

				// Checks to see if any of the emails between the 2 lists match.
				// The comparison between the emails ignores case
				if (emails != null && lSigContact.getEmails() != null) {
					Set<String> lEmails = new HashSet<String>();
					for (String lEmail : emails) {
						lEmails.add(lEmail.toLowerCase());
					}

					for (String lEmail : lSigContact.getEmails()) {
						if (lEmails.contains(lEmail.toLowerCase())) {
							lResult = true;
							break;
						}
					}
				}

				if (!lResult) {
					// If the emails did not match, then the phone numbers are
					// checked. Similarly, if there is one phone number that is
					// common to both lists, then it is considered a match.
					// Because there are different ways to write a phone number,
					// the comparison is done by removing all special
					// characters. The limitation is that if there is a phone
					// number like 1-555-555-2524 and 555-555-2524, they will
					// not match
					if (phone_numbers != null && lSigContact.getPhone_numbers() != null) {
						Set<String> lPhoneNumbers = new HashSet<String>();
						for (SigCapturePhoneNumber lPhoneNumber : phone_numbers) {
							if (!StringUtils.isEmpty(lPhoneNumber.getPhone_number())) {
								lPhoneNumbers.add(SigCaptureUtilities.stripPhoneNumber(lPhoneNumber.getPhone_number()));
							}
						}

						for (SigCapturePhoneNumber lPhoneNumberCompare : lSigContact.getPhone_numbers()) {
							if (lPhoneNumbers.contains(SigCaptureUtilities.stripPhoneNumber(lPhoneNumberCompare
									.getPhone_number()))) {
								lResult = true;
								break;
							}
						}
					}
				}
			}
		}

		return lResult;
	}
}
