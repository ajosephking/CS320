/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: JUnit test file that makes sure our Appointment Service class is meeting all of our requirements.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

// JUnit tools necessary to run several tests throughout this file
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// Imports both the Appointment and Appointment Service class files.
import projectOne.Appointment;
import projectOne.AppointmentService;

class AppointmentServiceTest {
	
	/*
	 * Establishes all variables that will be used in each of our tests.
	 */
	static AppointmentService appointmentService;
	static Date goodDate;
	static Date badDate;
	static Appointment appointment;
	static Appointment lowerBoundary;
	static Appointment upperBoundary;
	
	/*
	 * Sets all testing variables to appropriate values before testing begins. Since the requirements
	 * do not have an "update" method, we know these variables will remain unchanged throughout the
	 * testing process; the "appointment" will simply be added to and deleted from the "appointmentService"
	 * as needed in each test.
	 */
	@BeforeAll
	@DisplayName("Create testing components")
	static void setUp() {
		
		// Creates an appointment service object.
		appointmentService = new AppointmentService();
				
		// Creates a known, good date using the current time, plus one week (in milliseconds).
		long milliseconds = System.currentTimeMillis();
		goodDate = new Date(milliseconds + 604800000);
		
		// Creates a bad date set before today.
		badDate = new Date(1774999923);
		
		/* 
		 * Creates known, good appointments for use in each test: one in the middle of the good partitions,
		 * one using the lower boundaries of the good partitions, and one using the upper boundaries of the
		 * good partitions.
		 */
		appointment = new Appointment("12345",goodDate,"One week from today.");
		lowerBoundary = new Appointment("1",goodDate,"1");
		upperBoundary = new Appointment("1234567890",goodDate,"One week from today which is a lot of milliseconds");
	}
	
	
	/*
	 * Clears the appointment service database so each test has a fresh start.
	 */
	@BeforeEach
	@DisplayName("Clean up database")
	void clearDatabase() {
		appointmentService.getDatabase().clear();
	}
	
	/*
	 * Tests if the appointment service object was created successfully by checking if it is null.
	 */
	@Test
	@DisplayName("Check appointment service was created")
	void testCreationSuccess() {
		assertNotNull(appointmentService);
	}
	
	/*
	 * Tests if the addAppointment function successfully adds a new appointment into the database.
	 * Creates a new appointment, attempts to add the appointment into the database, and then confirms
	 * the appointment indeed exists inside of the database.
	 */
	@Test
	@DisplayName("Check good appointments can be added")
	void testAddAppointment() {
		
		// Checks the appointment was added and the appointment service contains the appointment.
		assertTrue(appointmentService.addAppointment(appointment));
		assertEquals(appointmentService.getAppointment(appointment.getId()), appointment);
		
		// Checks the lowerBoundary appointment was added and the appointment service contains lowerBoundary.
		assertTrue(appointmentService.addAppointment(lowerBoundary));
		assertEquals(appointmentService.getAppointment(lowerBoundary.getId()), lowerBoundary);
		
		// Checks the upperBoundary appointment was added and the appointment service contains upperBoundary.
		assertTrue(appointmentService.addAppointment(upperBoundary));
		assertEquals(appointmentService.getAppointment(upperBoundary.getId()), upperBoundary);
	}
	
	/*
	 * Tests if the deleteAppointment function truly removes an appointment from the appointment database.
	 * This adds an appointment to the database, attempts to delete that appointment from the database, 
	 * then confirms that the appointment no longer exists in the database.
	 */
	@Test
	@DisplayName("Check appointments can be deleted")
	void testDeleteAppointment() {
		
		// Checks the appointment was added, deleted, and no longer inside of the database (in that order).
		assertTrue(appointmentService.addAppointment(appointment));
		assertTrue(appointmentService.deleteAppointment("12345"));
		
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {appointmentService.getAppointment(appointment.getId());});

		assertTrue(thrown.getMessage().equals("Appointment not found."));
	}
	
	/*
	 * Failure parameters for attempting to add an appointment with bad data.
	 * These lines also include a failure tag at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		",One week from today.,ID",							// null ID
		"12345678910,One week from today.,ID",				// ID too long (11-character boundary condition)
		"123456,,Description",								// null description
		"123456,One week from today which is a lot of milliseconds.,Description",		// description too long (51-character boundary condition)
	})
	
	// Test for attempting to add appointments with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check add appointment failures (ID and Description)")
	void testAddFailure(String appointmentId, String appointmentDescription, String failureTag) {
				
		/* 
		 * Checks the good appointment was added, then attempts to re-add the same appointment, confirming existing
		 * appointments are not added to the database. 
		 */
		assertTrue(appointmentService.addAppointment(appointment));
		assertFalse(appointmentService.addAppointment(appointment));
		
		/* 
		 * NOTE: Date objects cannot be passed using @CsvSource in JUnit (at least not without an overly complicated
		 * method of converting passed strings into dates, which would muddy our tests). This is because java.util.Date 
		 * is a mostly deprecated class that is only used for legacy systems. However, our requirements specifically
		 * asked us to use java.util.Date, so I opted to work around the class instead of replacing it. To do so,
		 * I created separate tests for null date failures and bad date failures.
		 */
		// Assigns failing parameters from our csvsource to an illegal argument exception named "thrown".
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.addAppointment(new Appointment(appointmentId,goodDate,appointmentDescription));
		});
		
		/*
		 * Each failure type has an associated tag in the csv source, and that tag is concatenated 
		 * with the phrase " not valid." to produce another failure message. If the failure message 
		 * generated by the real Illegal Argument Exception matches the concatenated message, it 
		 * means the expected failure occurred and the task addition failed.
		 */
		assertTrue(thrown.getMessage().equals("Appointment " + failureTag + " not valid."));
		
		/*
		 * Finally, we check our that our database does not contain any appointments with IDs that match the appointments
		 * with bad data, meaning the new appointments with bad data were truly not added.
		 */
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.getAppointment(appointmentId);
			});
	}
	
	/*
	 * Test attempts to add an appointment to our database that has a null date. Just like the test above,
	 * this assigns the failure to an illegal argument exception named "thrown", and then checks if the
	 * failure message of the test matches the one generated by the illegal argument exception.
	 */
	@Test
	@DisplayName("Check add appointment failures (null date)")
	void testNullDateFailure() {
		
		// Assigns failing parameters from our csvsource to an illegal argument exception named "thrown".
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.addAppointment(new Appointment("12345",null,"One week from today."));
		});
		
		// Compares the generated error message with the expected error message.
		assertTrue(thrown.getMessage().equals("Appointment Date not valid."));
		
		/*
		 * Finally, we check our that our database does not contain an appointment with an ID that matches the 
		 * appointment with bad data, meaning the new appointment with bad data was truly not added.
		 */
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.getAppointment("12345");
			});
	}
	
	/*
	 * Test attempts to add an appointment to our database that has an old date. Just like the tests above,
	 * this assigns the failure to an illegal argument exception named "thrown", and then checks if the
	 * failure message of the test matches the one generated by the illegal argument exception.
	 */
	@Test
	@DisplayName("Check add appointment failures (old date)")
	void testOldDateFailure() {
		
		// Assigns failing parameters from our csvsource to an illegal argument exception named "thrown".
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.addAppointment(new Appointment("12345",badDate,"One week from today."));
		});
		
		// Compares the generated error message with the expected error message.
		assertTrue(thrown.getMessage().equals("Appointment Date not valid."));
		
		/*
		 * Finally, we check our that our database does not contain an appointment with an ID that matches the 
		 * appointment with bad data, meaning the new appointment with bad data was truly not added.
		 */
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.getAppointment("12345");
			});
	}
	
	/*
	 * Test attempts to delete an appointment that does not exist. A known, good appointment
	 * is added to the database, we check that it exists, then we try to delete an appointment
	 * using an appointmentId that does not match the existing appointment. Then we check to make sure
	 * the known, good appointment still exists within our database.
	 */
	@Test
	@DisplayName("Check delete appointment failures")
	void testDeleteFailure() {
		
		// Checks the appointment was added and the appointment service contains the appointment.
		assertTrue(appointmentService.addAppointment(appointment));
		
		/* 
		 * Attempts to delete an appointment that does not exist, which results in the error message
		 * "Appointment not found." This error message is assigned the name "thrown", and compared 
		 * with the expected error message string below.
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {appointmentService.deleteAppointment("54321");});

		assertTrue(thrown.getMessage().equals("Appointment not found."));

		// Finally, we check that our original good appointment still exists inside of our database.
		assertEquals(appointmentService.getAppointment(appointment.getId()), appointment);
	}
	
	/*
	 * Clears all data from testing variables once all tests are done.
	 */
	@AfterAll
	@DisplayName("Tear down all testing components")
	static void tearDown() {
		appointmentService = null;
		goodDate = null;
		badDate = null;
		appointment = null;
		lowerBoundary = null;
		upperBoundary = null;
	}
}
