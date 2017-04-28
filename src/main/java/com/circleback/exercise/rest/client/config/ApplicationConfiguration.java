package com.circleback.exercise.rest.client.config;

/**
 * Contains settings/properties and file paths for various parameters needed
 * across the application
 */
public class ApplicationConfiguration {

	public static String FILE_TO_INIT_CONTACTS = "src/main/resources/contact_list_to_merge_with";
	public static String FILE_TO_POST = "src/main/resources/contact_list_to_post";
	public static String FILE_TO_WRITE = "src/main/resources/merged_contact_list";

	public static String API_KEY = "X-CB-ApiKey";
	public static String BASE_URL = "https://api.circleback.com";
	public static String AUTHORIZATION_KEY = "YzhiZjY0ODAtMDQ1YS00YTJmLWJmZWUtNzlkNzc4ODFjZWZk";
	public static String EMAIL_CAPTURE_URL_EXTENSION = "/service/sig-capture/scan";
	public static String CACHE_CONTROL = "no-cache";

	public static String getEmailCaptureUrl() {
		return BASE_URL + EMAIL_CAPTURE_URL_EXTENSION;
	}
}
