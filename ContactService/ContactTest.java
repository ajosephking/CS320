/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: JUnit testing program for the Contact object file. Makes sure all of our
 * requirements are being met for the Contact object.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

// Several JUnit tools that make testing more compact and easier
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import projectOne.Contact;		// imports Contact object for testing

class ContactTest {

	/*
	 * List of parameters to create good contacts for testing.
	 */
	@CsvSource
	({
		"01,Shinji,Ikari,1234567891,777 Nerv Drive",									// good partition contact
		"1,1,1,1234567890,1",															// good partition contact (lower boundaries)
		"0000000001,ShinjiGetn,IkariGetin,1987654321,12345 The Longest Address psbl"	// good partition contact (upper boundaries)
	})
	
	@ParameterizedTest
	@DisplayName("Check contacts can be created.")
	/* 
	 * Tests if we can successfully create new contacts that meet all requirements, including
	 * those with information within the middle of the good partition, near the lower boundaries 
	 * of the good partition, and near the upper boundaries of the good partition.
	 */
	void testCreationSuccess(String userId, String firstName, String lastName, String phoneNumber, String address) {
		Contact contact = new Contact(userId, firstName, lastName, phoneNumber, address);
		assertTrue(contact.getId().equals(userId));
		assertTrue(contact.getFirstName().equals(firstName));
		assertTrue(contact.getLastName().equals(lastName));
		assertTrue(contact.getPhoneNumber().equals(phoneNumber));
		assertTrue(contact.getAddress().equals(address));
	}
	
	@Test
	@DisplayName("Check contacts can be mutated.")
	/*
	 * Tests if we can successfully update required information for a contact.
	 */
	void testMutationSuccess() {
		Contact contact = new Contact("01","Shinji","Ikari","1234567891","777 Nerv Drive");
		contact.setFirstName("Asuka");
		contact.setLastName("Soryu");
		contact.setPhoneNumber("1987654321");
		contact.setAddress("123 Komm Susser Todd Lane");
		assertTrue(contact.getId().equals("01"));				// checks ID is unchanged
		assertTrue(contact.getFirstName().equals("Asuka"));		// checks first name is changed
		assertTrue(contact.getLastName().equals("Soryu"));		// checks last name is changed
		assertTrue(contact.getPhoneNumber().equals("1987654321"));				// checks phone number is changed
		assertTrue(contact.getAddress().equals("123 Komm Susser Todd Lane"));	// checks address is changed
	}
	
	/*
	 * List of failing parameters for our contact creation failure test. Each entry
	 * tests out a different failure mode, from specific null entries to entries that
	 * are too long.
	 */
	@CsvSource({
		",Shinji,Ikari,1234567891,777 Nerv Drive",						// null ID
		"00000000001,Shinji,Ikari,1234567891,777 Nerv Drive",			// ID too long (11-character boundary)
		"01,,Ikari,1234567891,777 Nerv Drive",							// null first name
		"01,ShinjiGetIn,Ikari,1234567891,777 Nerv Drive",				// first name too long (11-character boundary)
		"01,Shinji,,1234567891,777 Nerv Drive",							// null last name
		"01,Shinji,IkariStopit,1234567891,777 Nerv Drive",				// last name too long (11-character boundary)
		"01,Shinji,Ikari,,777 Nerv Drive",								// null phone number
		"01,Shinji,Ikari,123456789,777 Nerv Drive",						// phone number too short (9-character boundary)
		"01,Shinji,Ikari,12345678911,777 Nerv Drive",					// phone number too long (11-character boundary)
		"01,Shinji,Ikari,OIIAOIIAOI,777 Nerv Drive",					// phone number contains non-digits
		"01,Shinji,Ikari,1234567891,",									// null address
		"01,Shinji,Ikari,1234567891,Zankoku na tenshi no you ni Sho",	//address too long (31-character boundary)
	})
	
	/* 
	 * Creation failure test that runs the above parameters to make sure no illegal
	 * argument exceptions are thrown upon creating a new contact object. Each line
	 * of the csvsource above is tested as a new contact object, and they all have
	 * at least one variable that should fail and throw an exception.
	 */
	@ParameterizedTest
	@DisplayName("Check contact creation failures.")
	void testCreationFailure(String userId, String firstName, String lastName, String phoneNumber, String address) {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact(userId,firstName,lastName,phoneNumber,address);
		});
	}
	
	/*
	 * Short list of failing parameters for updating a contact's first name.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",						// null first name
		"ShinjiGetIn,",				// first name too long (11-character boundary)
	})
	
	/*
	 * Tests all of the above failing parameters with the setFirstName mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check first name mutation failures.")
	void testFirstNameMutationFailure(String firstName) {
		Contact contact = new Contact("01","Shinji","Ikari","1234567891","777 Nerv Drive");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setFirstName(firstName);
		});
	}
	
	/*
	 * Short list of failing parameters for updating a contact's last name.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",					// null last name
		"IkariJumpIn,",			// last name too long (11-character boundary)
	})
	
	/*
	 * Tests all of the above failing parameters with the setLastName mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check last name mutation failures.")
	void testLastNameMutationFailure(String lastName) {
		Contact contact = new Contact("01","Shinji","Ikari","1234567891","777 Nerv Drive");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setLastName(lastName);
		});
	}
	
	/*
	 * Short list of failing parameters for updating a contact's phone number.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",				// null phone number
		"12345678910,",		// phone number too long (11-character boundary)
		"123456789,",		// phone number too short (9-character boundary)
		"OIIAOIIAOI,",		// phone number has non-digit characters
	})
	
	/*
	 * Tests all of the above failing parameters with the setPhoneNumber mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check phone number mutation failures.")
	void testPhoneNumberMutationFailure(String phoneNumber) {
		Contact contact = new Contact("01","Shinji","Ikari","1234567891","777 Nerv Drive");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setPhoneNumber(phoneNumber);
		});
	}
	
	/*
	 * Short list of failing parameters for updating a contact's address.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",									// null address
		"This is a verrrrry long address,",		// address too long (31-character boundary)
	})
	
	/*
	 * Tests all of the above failing parameters with the setAddress mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check address mutation failures.")
	void testAddressMutationFailure(String address) {
		Contact contact = new Contact("01","Shinji","Ikari","1234567891","777 Nerv Drive");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contact.setAddress(address);
		});
	}
}
