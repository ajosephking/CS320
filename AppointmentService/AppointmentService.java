/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: Task Service class used to store and delete Appointment objects
 * within a hashmap.
 */

package projectOne;

// Used to store all of our appointment objects
import java.util.HashMap;

public class AppointmentService {
	
	// Constructor for simple service object
	public AppointmentService() {}
	
	// HashMap to contain all input appointment objects
	HashMap<String, Appointment> appointmentDatabase = new HashMap<>();
	
	
	// Accessor method for testing only! Task database should remain private.
	public synchronized HashMap<String, Appointment> getDatabase() {
		
		return appointmentDatabase;
	}
	
	/*
	 * Method for retrieving an existing appointment from the appointment service database using
	 * an appointment ID. Throws an illegal argument exception with a special message if the appointment 
	 * is not found. Not part of the original requirements, but is used by our tests to verify appointments
	 * are successfully added or deleted from our appointment service database. Might be useful for future
	 * development of this application as well.
	 */
	public Appointment getAppointment(String appointmentId) throws IllegalArgumentException {
		Appointment existingAppointment = appointmentDatabase.get(appointmentId);
		
		if (existingAppointment == null) {
			throw new IllegalArgumentException("Appointment not found.");
		}
		
		return existingAppointment;
	}
	
	/* 
	 * Method for adding a new appointment to the appointment service database. Input appointment
	 * is added to the database if it does not exist (returns true), otherwise it will
	 * not add the appointment and return false.
	 */
	public boolean addAppointment(Appointment appointment) {
		return appointmentDatabase.putIfAbsent(appointment.getId(), appointment) == null;
	}
	
	/*
	 * Method for removing an appointment from the appointment service database. Uses the getAppointment
	 * function to locate an appointment first, then removes that entry from the appointment database if 
	 * there is a match (and returns true). Otherwise, throws an Illegal Argument Exception (using getAppointment)
	 * with a special message.
	 */
	public boolean deleteAppointment(String appointmentId) throws IllegalArgumentException {
		getAppointment(appointmentId);
		
		appointmentDatabase.remove(appointmentId);
		
		return true;
	}
}