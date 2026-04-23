/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: Contact Service class used to store, update, and delete Contact objects
 * within a hashmap.
 */

package projectOne;

// Used to store all of our contact objects
import java.util.HashMap;

public class ContactService {
	
	// HashMap to contain all input contact objects
	HashMap<String, Contact> database = new HashMap<>();

	// Constructor for simple service object
	public ContactService() {}
	
	// Accessor method for testing only! Database should remain private.
	public synchronized HashMap<String, Contact> getDatabase() {
		
		return database;
	}
	
	/*
	 * Method for retrieving an existing contact from the contact service database using a user ID. 
	 * Throws an illegal argument exception with a special message if the contact is not found.
	 * Not part of the original requirements, but is used by our tests to verify contacts are
	 * successfully added, deleted, or updated from our contact service database. Might be useful 
	 * for future development of this application as well.
	 */
	public Contact getContact(String userId) throws IllegalArgumentException{
		
		if (database.get(userId) == null) {
			throw new IllegalArgumentException("Contact not found.");
		}
		
		return database.get(userId);
	}
	
	/* 
	 * Method for adding a new contact to the contact service database. Input contact
	 * is added to the database if it does not exist (returns true), otherwise it will
	 * not add the contact and return false.
	 */
	public boolean addContact(Contact contact) {
		return database.putIfAbsent(contact.getId(), contact) == null;
	}
	
	/*
	 * Method for removing a contact from the contact service database. Uses the getContact 
	 * function to find a matching userID in the contact database first, then removes that 
	 * entry from the contact database if there is a match (and returns true). Otherwise, it 
	 * throws an Illegal Argument Exception using getContact with a special message.
	 */
	public boolean deleteContact(String userId) throws IllegalArgumentException {
		
		getContact(userId);
		database.remove(userId);
		return true;
	}
	
	/*
	 * Method for updating an existing contact's first name within the contact service database. 
	 * First, it checks if there is an existing contact object with a matching userID. If no such contact
	 * exists within the database, it throws an illegal argument exception. If there is indeed a matching 
	 * userID, it will set the first name to the new information and finally return true.
	 */
	public boolean updateFirstName(String userId, String updatedFirstName) throws IllegalArgumentException  {
		
		getContact(userId).setFirstName(updatedFirstName);
		return true;
	}
	
	/*
	 * Method for updating an existing contact's last name within the contact service database. 
	 * Uses the same principle as the updateFirstName method above.
	 */
	public boolean updateLastName(String userId, String updatedLastName) throws IllegalArgumentException  {
		
		getContact(userId).setLastName(updatedLastName);
		return true;
	}
	
	/*
	 * Method for updating an existing contact's phone number within the contact service database. 
	 * Uses the same principle as the updateFirstName method above.
	 */
	public boolean updatePhoneNumber(String userId, String updatedPhoneNumber) throws IllegalArgumentException  {
		
		getContact(userId).setPhoneNumber(updatedPhoneNumber);
		return true;
	}
	
	/*
	 * Method for updating an existing contact's address within the contact service database. 
	 * Uses the same principle as the updateFirstName method above.
	 */
	public boolean updateAddress(String userId, String updatedAddress) throws IllegalArgumentException  {
		
		getContact(userId).setAddress(updatedAddress);
		return true;
	}
}
