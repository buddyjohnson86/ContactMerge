README.txt

This java application makes a web service call to the CircleBack Email Signature Capture API passing in emails provided by CircleBack's development team. The Email Signature Capture API turns email signatures into contacts. The results of the service call (a contact list) are then merged them with a pre-defined list of contacts (provided by CircleBack's devleopment team) to produce a unique list of merged contacts. The merged output list is written to a file.

Instructions for running tests:
1. Checkout or Clone the project locally:
	git clone https://github.com/buddyjohnson86/ContactMerge.git
2. Go to the root directory of the project
3. Run: mvn clean
4. Run: mvn test

Additional steps to run webservice application:
5. Run: mvn package
6. Run: java -jar target/ContactMerge-0.0.1-SNAPSHOT.jar
7. ApplicationConfiguration has the authorization key: YzhiZjY0ODAtMDQ1YS00YTJmLWJmZWUtNzlkNzc4ODFjZWZk

Design Considerations:
1. When merging contacts, the following things were considered:
	a. 2 phone numbers were considered the same if after ignoring the country code for the US and any special characters, the digits matched.
	b. 2 emails or urls were considered the same, even if the cases did not match. test.com, tEst.com, and TEst.com are all considered the same.
	c. All fields within an Address and social profile had to match to be considered the same.

Issues:
1. There were some bugs in the provided files, so modifications were made. The "inms" field was not found in the API, so
those values were removed from the provided input files.

Application Overview:
1. This application uses SpringBoot to start up and inject all dependencies.
2. Upon startup, it immediately reads the contact_list_to_merge_with file, located in the resources folder and initializes a Map with the contacts.
3. It then submits a web service request from contact_list_to_post to the CircleBack EmailCapture service, takes the response and tries to merge with the initialized Map.
4. It then writes the merged list to a file (merged_contact_list).

Class Overview
1. The main method is in CircleBackApp.
2. The classes under com.circleback.exercise.email.signature.request contain the classes used to construct the parameters that go into the EmailCapture REST request. The json contents from contact_list_to_post are converted to those classes using jackson. 
3. The classes under com.circleback.exercise.email.signature.response contain the classes used to construct the response parameters that are returned by the EmailCapture service.
4. EmailCaptureRestClient contains the REST client that issues the webservice request.
5. ApplicationConfiguration contains properties for certain constants used across the application (authorization key, files to read, etc)
	a. Authorization Key is YzhiZjY0ODAtMDQ1YS00YTJmLWJmZWUtNzlkNzc4ODFjZWZk
6. ContactMergeService determines how to merge the contacts.
7. The classes under com.circleback.exercise.utility contain utilities for converting to/from json and comparing data.
8. There are some bare-bones unit tests. With additional time, more thorough tests could have been developed.
