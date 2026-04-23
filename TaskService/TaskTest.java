/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: JUnit testing program for the Task object file. Makes sure all of our
 * requirements are being met for the Task object.
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

// Several JUnit tools that make testing more compact and easier
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import projectOne.Task;		// imports Task object for testing

class TaskTest {

	/*
	 * List of parameters for good tasks to test we can create tasks at the upper boundaries, lower
	 * boundaries, and somewhere near the middle of the acceptable partitions for the taskID, taskName,
	 * and taskDescription.
	 */
	@CsvSource
	({
		"12345,Prize Task,Bring the most frustrating item.",	// good partition task
		"1,1,1",												// good partition task (lower boundaries)
		"1234567890,20character TaskName,Bring the utmost frustrating item for Greg Davies." 	// good partition task (upper boundaries)
	})
	
	@ParameterizedTest
	@DisplayName("Check tasks can be created.")
	/* 
	 * Tests if we can successfully create new tasks that meet all requirements, including boundary testing.
	 */
	void testCreationSuccess(String taskId, String taskName, String taskDescription) {
		Task task = new Task(taskId,taskName,taskDescription);
		assertTrue(task.getId().equals(taskId));
		assertTrue(task.getName().equals(taskName));
		assertTrue(task.getDescription().equals(taskDescription));
	}
	
	@Test
	@DisplayName("Check tasks can be mutated.")
	/*
	 * Tests if we can successfully update required information for a task.
	 */
	void testMutationSuccess() {
		Task task = new Task("12345","Prize Task","Bring the most frustrating item for Greg Davies");
		task.setName("Team Task");
		task.setDescription("Accurately describe Alex Horne\'s outfit");
		assertTrue(task.getId().equals("12345"));				// checks ID is unchanged
		assertTrue(task.getName().equals("Team Task"));			// checks task name is changed
		assertTrue(task.getDescription().equals("Accurately describe Alex Horne\'s outfit"));		// checks task description is changed
	}
	
	/*
	 * List of failing parameters for our task creation failure test. Each entry
	 * tests out a different failure mode, from specific null entries to entries that
	 * are too long.
	 */
	@CsvSource({
		",Prize Task,Bring the most frustrating item for Greg Davies",							// null ID
		"12345678910,Prize Task,Bring the most frustrating item for Greg Davies",				// ID too long (11-character boundary)
		"12345,,Bring the most frustrating item for Greg Davies",								// null task name
		"12345,21 character TaskName,Bring the most frustrating item for Greg Davies",			// task name too long (21-character boundary)
		"12345,Prize Task,",																	// null description
		"12345,Prize Task,Bring the utmost frustrating thing for Greg Davies.",					// description too long (51-character boundary)
	})
	
	/* 
	 * Creation failure test that runs the above parameters to make sure no illegal
	 * argument exceptions are thrown upon creating a new contact object. Each line
	 * of the csvsource above is tested as a new task object, and they all have
	 * at least one variable that should fail and throw an exception.
	 */
	@ParameterizedTest
	@DisplayName("Check task creation failures.")
	void testCreationFailure(String taskId, String taskName, String taskDescription) {
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(taskId,taskName,taskDescription);
		});
	}
	
	/*
	 * Short list of failing parameters for updating a task's name.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",								// null name
		"21 character TaskName,",			// name too long (21-character boundary failure)
	})
	
	/*
	 * Tests all of the above failing parameters with the setName mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check name mutation failures.")
	void testNameMutationFailure(String taskName) {
		Task task = new Task("12345","Prize Task","Bring the most frustrating item for Greg Davies");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			task.setName(taskName);
		});
	}
	
	/*
	 * Short list of failing parameters for updating a task's description.
	 * The same principle applied in the creation failure parameterized test
	 * applied here, and is repeated to test each mutator function.
	 */
	@CsvSource ({
		",",					// null description
		"Bring the utmost frustrating thing for Greg Davies.,",		// description too long (51-character boundary failure)
	})
	
	/*
	 * Tests all of the above failing parameters with the setDescription mutator
	 * to make sure an illegal argument exception is thrown for each.
	 */
	@ParameterizedTest
	@DisplayName("Check description mutation failures.")
	void testDescriptionMutationFailure(String taskDescription) {
		Task task = new Task("12345","Prize Task","Bring the most frustrating item for Greg Davies");
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			task.setDescription(taskDescription);
		});
	}
}
