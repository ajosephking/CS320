/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: Appointment class file used to create appointment objects with a unique ID, date, and description.
 */

package projectOne;

import java.util.Date;

public class Appointment {

	private final String appointmentId;
	private Date appointmentDate;
	private String appointmentDescription;
	
	// Constructor for our Appointment class.
	public Appointment(String appointmentId, Date appointmentDate, String appointmentDescription) {
		super();
		
		// Throws an exception if the ID is null or greater than 10 characters.
		if (appointmentId == null || appointmentId.length() > 10) {
			throw new IllegalArgumentException("Appointment ID not valid.");
		}
		
		// Throws an exception if the date of the appointment is null or before the current date.
		if (appointmentDate == null || appointmentDate.before(new Date())) {
			throw new IllegalArgumentException("Appointment Date not valid.");
		}
		
		// Throws an exception if the appointment description is null or greater than 50 characters.
		if (appointmentDescription == null || appointmentDescription.length() > 50) {
			throw new IllegalArgumentException("Appointment Description not valid.");
		}
		
		// If no exceptions are found, task is created using all passed parameters
		this.appointmentId = appointmentId;
		this.appointmentDate = appointmentDate;
		this.appointmentDescription = appointmentDescription;
	}
	
	// Accessor functions for Appointment class
	public String getId() {
		return appointmentId;
	}
	
	public Date getDate() {
		return appointmentDate;
	}
	
	public String getDescription() {
		return appointmentDescription;
	}
	
	// Mutator Functions for Appointment Class. Note the appointmentID is not included so it remains immutable.
	public void setDate(Date l_date) {
		
		// Throws an exception if the input date is null or before the current date.
		if (l_date == null || l_date.before(new Date())) {
			throw new IllegalArgumentException("New appointment date not valid.");
		}
		
		this.appointmentDate = l_date;
	}
	
	public void setDescription(String l_description) {
		
		// Throws an exception if the input description is null or greater than 50 characters.
		if (l_description == null || l_description.length() > 50) {
			throw new IllegalArgumentException("New description not valid.");
		}
		
		this.appointmentDescription = l_description;
	}
}
