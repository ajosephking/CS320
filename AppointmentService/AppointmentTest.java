/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: JUnit testing program for the Appointment object file. Makes sure all of our
 * requirements are being met for the Appointment object.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

// Several JUnit tools that make testing more compact and easier
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import projectOne.Appointment;		// imports Appointment object for testing

class AppointmentTest {
	
	/*
	 * Parameters to test acceptable inputs within the good partition: one in the middle of the partition, one at the
	 * lower boundary, and one at the upper boundary.
	 */
	@CsvSource
	({
		"12345,One week from today.",										// Good partition appointment
		"1,1",																// Good partition appointment (lower boundary)
		"1234567890,One week from today which is a lot of milliseconds",	// Good partition appointment (upper boundary)
	})
	
	/* 
	 * Tests if we can successfully create a new appointment that meets all requirements.
	 */
	@ParameterizedTest
	@DisplayName("Check appointments can be created.")
	void testCreationSuccess(String appointmentId, String appointmentDescription) {
		
		// Known, good date using the current time, plus one week (in milliseconds) to create an appointment date.
		long milliseconds = System.currentTimeMillis();
		Date goodDate = new Date(milliseconds + 604800000);
		
		// Creates a good appointment that meets all requirements.
		Appointment appointment = new Appointment(appointmentId, goodDate,appointmentDescription);
		
		// Tests assert all appointment variables are created and match expected inputs.
		assertTrue(appointment.getId().equals(appointmentId));
		assertTrue(appointment.getDate().equals(goodDate));
		assertTrue(appointment.getDescription().equals(appointmentDescription));
	}
	
	/*
	 * Tests if we can successfully update required information for an appointment.
	 */
	@Test
	@DisplayName("Check appointments can be mutated.")
	void testMutationSuccess() {
		
		// Known, good date using the current time, plus one week (in milliseconds) to create an appointment date.
		long milliseconds = System.currentTimeMillis();
		Date goodDate = new Date(milliseconds + 604800000);
		
		// Creates a good appointment to use in the test.
		Appointment appointment = new Appointment("12345", goodDate,"One week from today.");
		
		// Creates a date at a time that is two weeks from today.
		Date updatedDate = new Date(milliseconds + (604800000 * 2));
		
		// Updates the appointment date and description.
		appointment.setDate(updatedDate);
		appointment.setDescription("Two weeks from today.");
		
		assertTrue(appointment.getId().equals("12345"));				// checks ID is unchanged
		assertTrue(appointment.getDate().equals(updatedDate));			// checks appointment date is changed
		assertTrue(appointment.getDescription().equals("Two weeks from today."));		// checks appointment description is changed
	}
	
	/*
	 * List of failing parameters for our appointment creation failure test. Each entry
	 * tests out a different failure mode, from specific null entries to entries that
	 * are too long.
	 */
	@CsvSource({
		",One week from today.",						// null ID
		"12345678910,One week from today.",				// ID too long (11-character boundary failure)
		"12345,",										// null description
		"12345,One week from today which is a lot of milliseconds.",		// description too long (51-character boundary failure)
	})
	
	/* 
	 * Creation failure test that runs the above parameters to make sure no illegal
	 * argument exceptions are thrown upon creating a new contact object. Each line
	 * of the csvsource above is tested as a new appointment object, and they all have
	 * at least one variable that should fail and throw an exception.
	 */
	@ParameterizedTest
	@DisplayName("Check appointment creation failures.")
	void testCreationFailure(String appointmentId, String appointmentDescription) {
		
		// Known, good date using the current time, plus one week (in milliseconds) to create an appointment date.
		long milliseconds = System.currentTimeMillis();
		Date goodDate = new Date(milliseconds + 604800000);
		
		// Creates a bad date set before today.
		Date badDate = new Date(1774999923);
		
		// Checks that attempting to create an appointment with a null date throws an illegal argument exception.
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("12345",null,"One week from today.");
		});
		
		// Checks that attempting to create an appointment with a date before today throws an illegal argument exception.
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment("12345",badDate,"One week from today.");
		});
		
		// Checks all other failing parameters from our csvsource.
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(appointmentId,goodDate,appointmentDescription);
		});
	}
	
	
	/*
	 * Tests the failing parameters for changing appointment dates with the setDate mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@Test
	@DisplayName("Check date mutation failures.")
	void testDateMutationFailure() {
		
		// Known, good date using the current time, plus one week (in milliseconds) to create an appointment date.
		long milliseconds = System.currentTimeMillis();
		Date goodDate = new Date(milliseconds + 604800000);
				
		// Known, bad date set before now.
		Date badDate = new Date(1775010029);
		
		// Creates a good appointment to use in the test.
		Appointment appointment = new Appointment("12345", goodDate,"One week from today.");
		
		// Checks that a null date will throw an illegal argument exception
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointment.setDate(null);
		});
		
		// Checks that a date in the past will throw an illegal argument exception
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointment.setDate(badDate);
		});
	}
	
	/*
	 * Short list of failing parameters for updating an appointment's description.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",		// null description
		"One week from today which is a lot of milliseconds.,",		// description too long (51-character boundary failure)
	})
	
	/*
	 * Tests all of the above failing parameters with the setDescription mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check description mutation failures.")
	void testDescriptionMutationFailure(String appointmentDescription) {
		
		// Known, good date using the current time, plus one week (in milliseconds) to create an appointment date.
		long milliseconds = System.currentTimeMillis();
		Date goodDate = new Date(milliseconds + 604800000);
		
		// Creates a good appointment to use in the test.
		Appointment appointment = new Appointment("12345", goodDate,"One week from today.");
		
		// Checks that all failing parameters will throw an illegal argument exception.
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointment.setDescription(appointmentDescription);
		});
	}
}
