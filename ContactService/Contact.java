/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: Contact class file used to create contact objects with a unique ID,
 * First Name, Last Name, Phone Number, and Address.
 */

package projectOne;

public class Contact {

	private final String userId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	
	// Constructor for our Contact class.
	public Contact(String userId, String firstName, String lastName, String phoneNumber, String address) {
		super();
		
		// Throws an exception if the ID is null or greater than 10 characters
		if (userId == null || userId.length() > 10) {
			throw new IllegalArgumentException("ID not valid.");
		}
		
		// Throws an exception if the first name is null or greater than 10 characters
		if (firstName == null || firstName.length() > 10) {
			throw new IllegalArgumentException("First name not valid.");
		}
		
		// Throws an exception if the last name is null or greater than 10 characters
		if (lastName == null || lastName.length() > 10) {
			throw new IllegalArgumentException("Last name not valid.");
		}
		
		// Throws an exception if the phone number is null, not exactly 10 characters, or contains any non-digit characters
		if (phoneNumber == null || phoneNumber.length() != 10 || phoneNumber.matches(".*\\D+.*")) {
			throw new IllegalArgumentException("Phone number not valid.");
		}
		
		// Throws an exception if the address is null or greater than 30 characters
		if (address == null || address.length() > 30) {
			throw new IllegalArgumentException("Address not valid.");
		}
		
		// If no exceptions are found, contact is created using all passed parameters
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}
	
	// Accessor functions for Contacts class
	public String getId() {
		return userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	// Mutator Functions for Contacts Class. Note the userID is not included so it remains immutable.
	public void setFirstName(String l_firstName) {
		
		// Throws an exception if the input first name is null or greater than 10 characters.
		if (l_firstName == null || l_firstName.length() > 10) {
			throw new IllegalArgumentException("New first name not valid.");
		}
		
		this.firstName = l_firstName;
	}
	
	public void setLastName(String l_lastName) {
		
		// Throws an exception if the input last name is null or greater than 10 characters.
		if (l_lastName == null || l_lastName.length() > 10) {
			throw new IllegalArgumentException("New last name not valid.");
		}
		
		this.lastName = l_lastName;
	}
	
	public void setPhoneNumber(String l_phoneNumber) {
		
		// Throws an exception if the input phone number is null, not exactly 10 characters, or contains non-digits.
		if (l_phoneNumber == null || l_phoneNumber.length() != 10 || l_phoneNumber.matches(".*\\D+.*")) {
			throw new IllegalArgumentException("New number not valid.");
		}
		
		this.phoneNumber = l_phoneNumber;
	}
	
	public void setAddress(String l_address) {
		
		// Throws an exception if the input address is null or greater than 30 characters.
		if (l_address == null || l_address.length() > 30) {
			throw new IllegalArgumentException("New address not valid.");
		}
		
		this.address = l_address;
	}
	
}
