/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: JUnit test file that makes sure our Contact Service class is meeting all
 * of our requirements.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

// JUnit tools necessary to run several tests throughout this file
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Imports both the Contact and Contact Service class files.
import projectOne.Contact;
import projectOne.ContactService;

class ContactServiceTest {
	
	/*
	 * Establishes variables that will be used in our tests.
	 */
	static ContactService contactService;
	static Contact contact;
	static Contact lowerBoundary;
	static Contact upperBoundary;
	
	/*
	 * Sets all testing variables to appropriate values before testing begins.
	 */
	@BeforeAll
	@DisplayName("Create testing components")
	static void setUp() {
		contactService = new ContactService();
		
		contact = new Contact("01","Shinji","Ikari","1234567891","777 Nerv Drive");
		lowerBoundary = new Contact("1","1","1","1234567890","1");
		upperBoundary = new Contact("0000000001","ShinjiGetn","IkariGetin","1987654321","12345 The Longest Address psbl");
	}
	
	/*
	 * Clears out any old data created by each test from the database.
	 */
	@BeforeEach
	void clearDatabase() {
		contactService.getDatabase().clear();
	}
	
	/*
	 * Tests if the instance of the contact service has been created successfully. 
	 */
	@Test
	@DisplayName("Check contact service is created.")
	void testGetInstance() {
		assertNotNull(contactService);
	}
	
	/*
	 * Tests if the addContact function successfully adds a new contact into the database.
	 * Attempts to add each known, good contact into the database, and then confirms
	 * each contact indeed exists inside of the database.
	 */
	@Test
	@DisplayName("Check Contacts can be added.")
	void testAddContact() {
		assertTrue(contactService.addContact(contact));
		assertTrue(contactService.getContact(contact.getId()) == contact);
		
		assertTrue(contactService.addContact(lowerBoundary));
		assertTrue(contactService.getContact(lowerBoundary.getId()) == lowerBoundary);
		
		assertTrue(contactService.addContact(upperBoundary));
		assertTrue(contactService.getContact(upperBoundary.getId()) == upperBoundary);
	}
	
	/*
	 * Tests if the deleteContact function truly removes a contact from the database.
	 * This adds a new contact to the database, attempts to delete the contact
	 * from the database, then confirms that the contact no longer exists.
	 */
	@Test
	@DisplayName("Check contacts can be deleted.")
	void testDeleteContact() {
		
		assertTrue(contactService.addContact(contact));
		assertTrue(contactService.deleteContact("01"));
		
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				()-> {
					contactService.getContact(contact.getId());
				});
		assertTrue(thrown.getMessage().equals("Contact not found."));
		
	}

	/*
	 * Tests if the updateFirstName function truly updates contact's first name using the contact userID.
	 * This adds a known, good contact to the database, attempts to update the contact's first name
	 * using the updateFirstName function, then checks that the first name matches the updated contact's values.
	 */
	@Test
	@DisplayName("Check contact first names can be updated.")
	void testUpdateFirstName() {
		
		assertTrue(contactService.addContact(contact));
		String updatedFirstName = "Asuka";
		
		assertTrue(contactService.updateFirstName("01", updatedFirstName));
		assertTrue(contactService.getContact("01").getFirstName() == "Asuka");
		
	}
	
	/*
	 * Tests if the updateLasttName function truly updates contact's last name using the contact userID.
	 * This adds a known, good contact to the database, attempts to update the contact's last name
	 * using the updateLastName function, then checks that the last name matches the updated contact's values.
	 */
	@Test
	@DisplayName("Check contact last names can be updated.")
	void testUpdateLastName() {
		
		assertTrue(contactService.addContact(contact));
		String updatedLastName = "Soryu";
		
		assertTrue(contactService.updateLastName("01", updatedLastName));
		assertTrue(contactService.getContact("01").getLastName() == "Soryu");
		
	}
	
	/*
	 * Tests if the updatePhoneNumber function truly updates contact's phone number using the contact userID.
	 * This adds a known, good contact to the database, attempts to update the contact's phone number
	 * using the updatePhoneNumber function, then checks that the phone number matches the updated contact's values.
	 */
	@Test
	@DisplayName("Check contact phone numbers can be updated.")
	void testUpdatePhoneNumber() {
		
		assertTrue(contactService.addContact(contact));
		String updatedPhoneNumber = "9876543210";
		
		assertTrue(contactService.updatePhoneNumber("01", updatedPhoneNumber));
		assertTrue(contactService.getContact("01").getPhoneNumber() == "9876543210");
		
	}
	
	/*
	 * Tests if the updateAddress function truly updates contact's address using the contact userID.
	 * This adds a known, good contact to the database, attempts to update the contact's address
	 * using the updateAddress function, then checks that the address matches the updated contact's values.
	 */
	@Test
	@DisplayName("Check contact addresses can be updated.")
	void testUpdateAddress() {
		
		assertTrue(contactService.addContact(contact));
		String updatedAddress = "666 Komm Susser Todd Ln.";
		
		assertTrue(contactService.updateAddress("01", updatedAddress));
		assertTrue(contactService.getContact("01").getAddress() == "666 Komm Susser Todd Ln.");
		
	}
	
	/*
	 * Failure parameters for attempting to add a contact with bad data.
	 * These lines also include a failure tag at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		",Shinji,Ikari,1234567891,777 Nerv Drive,ID",								// null ID
		"00000000001,Shinji,Ikari,1234567891,777 Nerv Drive,ID",					// ID too long (11-character boundary)
		"02,,Ikari,1234567891,777 Nerv Drive,First name",							// null first name
		"02,ShinjiGetIn,Ikari,1234567891,777 Nerv Drive,First name",				// first name too long (11-character boundary)
		"02,Shinji,,1234567891,777 Nerv Drive,Last name",							// null last name
		"02,Shinji,IkariGettin,1234567891,777 Nerv Drive,Last name",				// last name too long (11-character boundary)
		"02,Shinji,Ikari,,777 Nerv Drive,Phone number",								// null phone number
		"02,Shinji,Ikari,123456789,777 Nerv Drive,Phone number",					// phone number too short (9-character boundary)
		"02,Shinji,Ikari,12345678911,777 Nerv Drive,Phone number",					// phone number too long (11-character boundary)
		"02,Shinji,Ikari,OIIAOIIAOI,777 Nerv Drive,Phone number",					// phone number contains non-digits
		"02,Shinji,Ikari,1234567891,,Address",										// null address
		"02,Shinji,Ikari,1234567891,Zankoku na tenshi no you ni Sho,Address",		// address too long (31-character boundary)
	})
	
	// Test for attempting to add contacts with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check add contact failures.")
	void testAddFailure(String userId, String firstName, String lastName, String phoneNumber, String address, String failureTag) {
		
		// First the test adds a known, good contact to the database.
		assertTrue(contactService.addContact(contact));
		
		// Next, it attempts to re-add the contact to the database, resulting in a failure
		assertFalse(contactService.addContact(contact));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to add a new contact with bad data.
		 * These failure parameters are pulled from the csvsource above, and each failure
		 * throws an exception unique to the failure (a bad first name will list "First name
		 * not valid," etc.). Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {
					contactService.addContact(new Contact(userId,firstName,lastName,phoneNumber,address));
					});
		
		/*
		 * Each failure type has an associated tag in the csv source, and that tag is concatenated 
		 * with the phrase " not valid." to produce another failure message. If the failure message 
		 * generated by the real Illegal Argument Exception matches the concatenated message, it 
		 * means the expected failure occurred and the contact addition failed.
		 */
		assertTrue(thrown.getMessage().equals(failureTag + " not valid."));
		
		/*
		 * Finally, we check our that our database does not contain any entry with an ID that matches
		 * the bad contact's ID, meaning the new contact with bad data was truly not added.
		 */
		Assertions.assertThrows(IllegalArgumentException.class, 
				()-> {contactService.getContact(userId);}
				);
	}	
	
	/*
	 * Test attempts to delete a contact that does not exist. A known, good contact
	 * is added to the database, then we try to delete a contact using a userId that does not match 
	 * the existing contact. Then we check to make sure the known, good contact still exists within 
	 * our database.
	 */
	@Test
	@DisplayName("Check delete contact failures.")
	void testDeleteFailure() {
		assertTrue(contactService.addContact(contact));
		
		/* 
		 * Attempts to delete a task that does not exist, which results in the error message
		 * "Task not found." This error message is assigned the name "thrown", and compared 
		 * with the expected error message string below.
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {contactService.deleteContact("02");
				});

		assertTrue(thrown.getMessage().equals("Contact not found."));
		
		assertTrue(contactService.getContact("01") == contact);
	}
	
	/*
	 * Failure parameters for attempting to update an existing contact with bad first names.
	 * These lines also include a failure message at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		"01,,New first name not valid.",						// null first name
		"01,ShinjiGetIn,New first name not valid.",				// first name too long (11-character boundary)
		"02,Shinji,Contact not found."							// Contact does not exist
	})
	
	// Test for attempting to update contacts with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check update first name failures.")
	void testUpdateFirstNameFailure(String userId, String firstName, String failureMessage) {
		
		// First the test adds a known, good contact to the database.
		assertTrue(contactService.addContact(contact));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to update the known, good contact with bad data.
		 * These failure parameters are pulled from the csvsource above, and each failure
		 * throws an exception unique to the failure (a bad first name will list "New first name
		 * not valid," etc.). Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {contactService.updateFirstName(userId, firstName);
					});
		
		/*
		 * Each failure type has an associated message in the csv source. If the failure message 
		 * generated by the real Illegal Argument Exception matches the message in our failure
		 * parameters, it means the expected failure occurred and the data update failed.
		 */
		assertTrue(thrown.getMessage().equals(failureMessage));
		
		/*
		 * Finally, we check our existing, good contact to make sure it remains unchanged in the
		 * database, confirming that no bad data was added to that contact.
		 */
		assertTrue(contactService.getContact("01") == contact);	
	}
	
	/*
	 * Failure parameters for attempting to update an existing contact with bad last names.
	 * These lines also include a failure message at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		"01,,New last name not valid.",						// null last name
		"01,IkariGettIn,New last name not valid.",			// last name too long (11-character boundary)
		"02,Ikari,Contact not found."						// Contact does not exist
	})
	
	// Test for attempting to update contact last names with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check update last name failures.")
	void testUpdateLastNameFailure(String userId, String lastName, String failureMessage) {
		
		// First the test adds a known, good contact to the database.
		assertTrue(contactService.addContact(contact));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to update the known, good contact with bad data.
		 * These failure parameters are pulled from the csvsource above, and each failure
		 * throws an exception unique to the failure. Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {contactService.updateLastName(userId, lastName);
					});
		
		/*
		 * Each failure type has an associated message in the csv source. If the failure message 
		 * generated by the real Illegal Argument Exception matches the message in our failure
		 * parameters, it means the expected failure occurred and the data update failed.
		 */
		assertTrue(thrown.getMessage().equals(failureMessage));
		
		/*
		 * Finally, we check our existing, good contact to make sure it remains unchanged in the
		 * database, confirming that no bad data was added to that contact.
		 */
		assertTrue(contactService.getContact("01") == contact);	
	}	
	
	/*
	 * Failure parameters for attempting to update an existing contact with bad phone numbers.
	 * These lines also include a failure message at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		"01,,New number not valid.",						// null phone number
		"01,123456789,New number not valid.",				// phone number too short (9-character boundary)
		"01,12345678911,New number not valid.",				// phone number too long (11-character boundary)
		"01,OIIAOIIAOI,New number not valid.",				// phone number contains non-digits
		"02,1234567890,Contact not found."					// Contact does not exist
	})
	
	// Test for attempting to update contact phone numbers with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check update phone number failures.")
	void testUpdatePhoneNumberFailure(String userId, String phoneNumber, String failureMessage) {
		
		// First the test adds a known, good contact to the database.
		assertTrue(contactService.addContact(contact));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to update the known, good contact with bad data.
		 * These failure parameters are pulled from the csvsource above, and each failure
		 * throws an exception unique to the failure. Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {contactService.updatePhoneNumber(userId, phoneNumber);
					});
		
		/*
		 * Each failure type has an associated message in the csv source. If the failure message 
		 * generated by the real Illegal Argument Exception matches the message in our failure
		 * parameters, it means the expected failure occurred and the data update failed.
		 */
		assertTrue(thrown.getMessage().equals(failureMessage));
		
		/*
		 * Finally, we check our existing, good contact to make sure it remains unchanged in the
		 * database, confirming that no bad data was added to that contact.
		 */
		assertTrue(contactService.getContact("01") == contact);	
	}
	
	/*
	 * Failure parameters for attempting to update an existing contact with bad addresses.
	 * These lines also include a failure message at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		"01,,New address not valid.",									// null address
		"01,Zankoku na tenshi no you ni Sho,New address not valid.",	// address too long (31-character boundary)
		"02,777 Nerv Drive,Contact not found."							// Contact does not exist
	})
	
	// Test for attempting to update contact addresses with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check update phone number failures.")
	void testUpdateAddressFailure(String userId, String address, String failureMessage) {
		
		// First the test adds a known, good contact to the database.
		assertTrue(contactService.addContact(contact));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to update the known, good contact with bad data.
		 * These failure parameters are pulled from the csvsource above, and each failure
		 * throws an exception unique to the failure. Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {contactService.updateAddress(userId, address);
					});
		
		/*
		 * Each failure type has an associated message in the csv source. If the failure message 
		 * generated by the real Illegal Argument Exception matches the message in our failure
		 * parameters, it means the expected failure occurred and the data update failed.
		 */
		assertTrue(thrown.getMessage().equals(failureMessage));
		
		/*
		 * Finally, we check our existing, good contact to make sure it remains unchanged in the
		 * database, confirming that no bad data was added to that contact.
		 */
		assertTrue(contactService.getContact("01") == contact);	
	}
	
	/*
	 * Clears all data from testing variables once all tests are done.
	 */
	@AfterAll
	@DisplayName("Tear down all testing components")
	static void tearDown() {
		contactService = null;
		contact = null;
		lowerBoundary = null;
		upperBoundary = null;
	}
}
