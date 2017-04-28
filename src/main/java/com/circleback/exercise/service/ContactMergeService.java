package com.circleback.exercise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.circleback.exercise.email.signature.response.SigCaptureContact;
import com.circleback.exercise.email.signature.response.SigCapturePhoneNumber;
import com.circleback.exercise.email.signature.response.SigCapturePostalAddress;
import com.circleback.exercise.email.signature.response.SigCaptureResponse;
import com.circleback.exercise.email.signature.response.SigCaptureResult;
import com.circleback.exercise.email.signature.response.SigCaptureSocialProfile;
import com.circleback.exercise.utility.SigCaptureUtilities;

/**
 * Merges contacts from the Email Capture Webservice with current set of
 * contacts
 */
@Service
public class ContactMergeService {

	/**
	 * Logger for class
	 */
	private static final Logger LOGGER = Logger.getLogger(ContactMergeService.class);

	/**
	 * Mapping of integer to SigCaptureContact object. Integer is hashcode
	 * representation of the first/last name of the contact
	 */
	private Map<Integer, HashSet<SigCaptureContact>> myCaptureContacts;

	/**
	 * Constructor for service. Initializes map of integers to
	 * SigCaptureContact.
	 */
	public ContactMergeService() {
		myCaptureContacts = new HashMap<Integer, HashSet<SigCaptureContact>>();
	}

	/**
	 * Pre-populates contact information in map. Assumes that current entry set
	 * should be cleared out. Assumes that SigCaptureContacts do not need to be
	 * merged. However, there could be cases where you have 2 SigCaptureContacts
	 * that have matching first and last names, but do not have matching emails
	 * and/or phone numbers. For those cases, both contacts are stored under the
	 * same key within a Set
	 * 
	 * @param sigCaptureContacts
	 *            list of SigCaptureContacts
	 */
	public void initCaptureContacts(List<SigCaptureContact> sigCaptureContacts) {
		myCaptureContacts = new HashMap<Integer, HashSet<SigCaptureContact>>();
		for (SigCaptureContact lSigContact : sigCaptureContacts) {
			if (!myCaptureContacts.containsKey(lSigContact)) {
				HashSet<SigCaptureContact> lSet = new HashSet<SigCaptureContact>();
				lSet.add(lSigContact);
				myCaptureContacts.put(lSigContact.hashCode(), new HashSet<SigCaptureContact>(lSet));
			} else {
				myCaptureContacts.get(lSigContact.hashCode()).add(lSigContact);
			}
		}
	}

	/**
	 * Return current list of contact information
	 * 
	 * @return List of SigCaptureContacts
	 */
	public List<SigCaptureContact> getCurrentContacts() {
		List<SigCaptureContact> lCurrentContacts = new ArrayList<SigCaptureContact>();
		for (HashSet<SigCaptureContact> lContacts : myCaptureContacts.values()) {
			lCurrentContacts.addAll(lContacts);
		}

		return lCurrentContacts;
	}

	/**
	 * Compares SigCaptureResponse with current set of contacts and determines
	 * if they match. If so, merges the attributes with current set of
	 * attributes. Otherwise, creates a new entry
	 * 
	 * @param sigCaptureResponse
	 */
	public void compareResponse(SigCaptureResponse sigCaptureResponse) {
		for (SigCaptureResult lSigCaptureResult : sigCaptureResponse.getResults()) {
			for (SigCaptureContact lSigCaptureContact : lSigCaptureResult.getContacts()) {
				// Check to see if contact exists. If so merge to existing
				// contact. If hashcode matches, but a match cannot be found in
				// the map, that indicates that the name (first/last name)
				// matches, but neither the emails nor phone numbers match. Add
				// to the set maintained for that hash code.
				if (myCaptureContacts.get(lSigCaptureContact.hashCode()) != null) {
					HashSet<SigCaptureContact> lSigCaptureContactSet = myCaptureContacts.get(lSigCaptureContact
							.hashCode());
					boolean lMatches = false;
					for (SigCaptureContact lSigCaptureContactCurrent : lSigCaptureContactSet) {
						if (lSigCaptureContact.equals(lSigCaptureContactCurrent)) {
							logSigCaptureContact("Contact info being merged for: ", lSigCaptureContact, false);
							mergeContact(lSigCaptureContactCurrent, lSigCaptureContact);
							LOGGER.info("Contacts merged\n");
							lMatches = true;
							break;
						}
					}

					if (!lMatches) {
						logSigCaptureContact("New contact info being added for existing name: ", lSigCaptureContact,
								true);
						lSigCaptureContactSet.add(lSigCaptureContact);
					}
				} else if (!StringUtils.isEmpty(lSigCaptureContact.getFirst_name())
						&& !StringUtils.isEmpty(lSigCaptureContact.getLast_name())) {
					// If cannot be matched and contact has at least a first or
					// last name, then add a new contact. Only contacts with at
					// least a first or last name will be added, otherwise
					// contacts without first and last names will be incorrectly
					// merged to each other, even though if they are for
					// different contacts
					HashSet<SigCaptureContact> lContactSet = new HashSet<SigCaptureContact>();
					lContactSet.add(lSigCaptureContact);
					myCaptureContacts.put(lSigCaptureContact.hashCode(), lContactSet);
					logSigCaptureContact("Contact is unique. Creating a new entry for: ", lSigCaptureContact, true);
				}
			}
		}
	}

	/**
	 * Merges contacts. Only adds an attribute if it is missing.
	 * 
	 * @param existingSigCaptureContact
	 * @param sigCaptureContactToMerge
	 */
	protected void mergeContact(SigCaptureContact existingSigCaptureContact, SigCaptureContact sigCaptureContactToMerge) {

		// Merge primitives first. Set attribute if existing attribute is empty
		if (StringUtils.isEmpty(existingSigCaptureContact.getCompany())) {
			existingSigCaptureContact.setCompany(sigCaptureContactToMerge.getCompany());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getFirst_name())) {
			existingSigCaptureContact.setFirst_name(sigCaptureContactToMerge.getFirst_name());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getFull_name())) {
			existingSigCaptureContact.setFull_name(sigCaptureContactToMerge.getFull_name());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getMiddle_name())) {
			existingSigCaptureContact.setMiddle_name(sigCaptureContactToMerge.getMiddle_name());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getLast_name())) {
			existingSigCaptureContact.setLast_name(sigCaptureContactToMerge.getLast_name());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getNick_name())) {
			existingSigCaptureContact.setNick_name(sigCaptureContactToMerge.getNick_name());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getPrefix())) {
			existingSigCaptureContact.setPrefix(sigCaptureContactToMerge.getPrefix());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getSuffix())) {
			existingSigCaptureContact.setSuffix(sigCaptureContactToMerge.getSuffix());
		}

		if (StringUtils.isEmpty(existingSigCaptureContact.getTitle())) {
			existingSigCaptureContact.setTitle(sigCaptureContactToMerge.getTitle());
		}

		// Merge lists. For urls, emails ignore case when determining if a url/email is already in the existing contact.
		existingSigCaptureContact.setUrls(mergeNewStrings(existingSigCaptureContact.getUrls(),
				sigCaptureContactToMerge.getUrls()));
		existingSigCaptureContact.setEmails(mergeNewStrings(existingSigCaptureContact.getEmails(),
				sigCaptureContactToMerge.getEmails()));
		
		//For phone numbers, ignore special characters and US country code when determining if phone number is already
		//in existing contact
		existingSigCaptureContact.setPhone_numbers(mergePhoneNumbers(existingSigCaptureContact.getPhone_numbers(),
				sigCaptureContactToMerge.getPhone_numbers()));
		
		//For social profile and addresses, only add if entire address/social profile is unique
		existingSigCaptureContact.setAddresses(mergeAddresses(existingSigCaptureContact.getAddresses(),
				sigCaptureContactToMerge.getAddresses()));
		existingSigCaptureContact.setSocial_profiles(mergeSocialProfiles(
				existingSigCaptureContact.getSocial_profiles(), sigCaptureContactToMerge.getSocial_profiles()));
	}

	protected SigCaptureSocialProfile[] mergeSocialProfiles(SigCaptureSocialProfile[] existingSocialProfiles,
			SigCaptureSocialProfile[] socialProfilesToMerge) {
		Set<SigCaptureSocialProfile> lMergedSocialProfiles = new HashSet<SigCaptureSocialProfile>();
		if (existingSocialProfiles != null && existingSocialProfiles.length > 0) {
			lMergedSocialProfiles.addAll(Arrays.asList(existingSocialProfiles));
		}

		if (socialProfilesToMerge != null && socialProfilesToMerge.length > 0) {
			lMergedSocialProfiles.addAll(Arrays.asList(socialProfilesToMerge));
		}

		return lMergedSocialProfiles.toArray(new SigCaptureSocialProfile[lMergedSocialProfiles.size()]);
	}

	protected SigCapturePostalAddress[] mergeAddresses(SigCapturePostalAddress[] existingAddresses,
			SigCapturePostalAddress[] addressesToMerge) {
		Set<SigCapturePostalAddress> lMergedAddresses = new HashSet<SigCapturePostalAddress>();
		if (existingAddresses != null && existingAddresses.length > 0) {
			lMergedAddresses.addAll(Arrays.asList(existingAddresses));
		}

		if (addressesToMerge != null && addressesToMerge.length > 0) {
			lMergedAddresses.addAll(Arrays.asList(addressesToMerge));
		}

		return lMergedAddresses.toArray(new SigCapturePostalAddress[lMergedAddresses.size()]);
	}

	protected SigCapturePhoneNumber[] mergePhoneNumbers(SigCapturePhoneNumber[] existingPhoneNumbers,
			SigCapturePhoneNumber[] phoneNumbersToMerge) {
		List<SigCapturePhoneNumber> lSigCapturePhoneNumber = new ArrayList<SigCapturePhoneNumber>();
		if (existingPhoneNumbers == null || existingPhoneNumbers.length == 0) {
			if (phoneNumbersToMerge != null) {
				lSigCapturePhoneNumber.addAll(Arrays.asList(phoneNumbersToMerge));
			}
		} else {
			if (phoneNumbersToMerge != null) {
				for (SigCapturePhoneNumber lSigPhoneNumber : phoneNumbersToMerge) {
					boolean lFound = false;
					for (SigCapturePhoneNumber lExistingSigPhoneNumber : existingPhoneNumbers) {
						if (SigCaptureUtilities.arePhoneNumbersEqual(lSigPhoneNumber.getPhone_number(),
								lExistingSigPhoneNumber.getPhone_number())) {
							lFound = true;
							SigCapturePhoneNumber lMergedNumber = new SigCapturePhoneNumber();
							lMergedNumber.setPhone_number(lExistingSigPhoneNumber.getPhone_number());
							if (StringUtils.isEmpty(lExistingSigPhoneNumber.getType())) {
								lMergedNumber.setType(lSigPhoneNumber.getType());
							} else {
								lMergedNumber.setType(lExistingSigPhoneNumber.getType());
							}
							lSigCapturePhoneNumber.add(lMergedNumber);
							break;
						}
					}
					if (!lFound) {
						lSigCapturePhoneNumber.add(lSigPhoneNumber);
					}
				}
			}
		}

		return lSigCapturePhoneNumber.toArray(new SigCapturePhoneNumber[lSigCapturePhoneNumber.size()]);
	}

	protected String[] mergeNewStrings(String[] existingList, String[] listToMerge) {
		List<String> lExistingList = new ArrayList<String>();
		if (existingList != null) {
			lExistingList.addAll(Arrays.asList(existingList));
			if (listToMerge != null) {
				for (String lMergeString : listToMerge) {
					for (String lExistingString : existingList) {
						if (!lExistingString.equalsIgnoreCase(lMergeString)) {
							lExistingList.add(lMergeString);
						}
					}
				}
			}
		} else if (listToMerge != null) {
			lExistingList.addAll(Arrays.asList(listToMerge));
		}

		return lExistingList.toArray(new String[lExistingList.size()]);
	}

	private void logSigCaptureContact(String message, SigCaptureContact contact, boolean appendNewLine) {
		StringBuilder lSB = new StringBuilder(message);
		if (!StringUtils.isEmpty(contact.getFirst_name())) {
			lSB.append(contact.getFirst_name());
			lSB.append(" ");
		}

		if (!StringUtils.isEmpty(contact.getLast_name())) {
			lSB.append(contact.getLast_name());
		}

		if (appendNewLine) {
			lSB.append("\n");
		}

		LOGGER.info(lSB.toString());
	}
}
