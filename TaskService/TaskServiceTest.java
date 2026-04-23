/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: JUnit test file that makes sure our Task Service class is meeting all of our requirements.
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

// Imports both the Task and Task Service class files.
import projectOne.Task;
import projectOne.TaskService;

class TaskServiceTest {
	
	/*
	 * Establishes all variables that will be used in our tests.
	 */
	static TaskService taskService;
	static Task task;
	static Task lowerBoundary;
	static Task upperBoundary;
	
	/*
	 * Sets all testing variables to appropriate values before testing begins.
	 */
	@BeforeAll
	@DisplayName("Create testing components")
	static void setUp() {
		
		// Creates a task service object.
		taskService = new TaskService();
		
		/* 
		 * Creates a known, good tasks for use in each test: one in the middle of the good partition,
		 * one using the lower boundaries of the good partition, and one using the upper boundaries
		 * of the good partition.
		 */
		task = new Task("12345","Prize Task","Bring the most frustrating item for Greg Davies");
		lowerBoundary = new Task("1","1","1");
		upperBoundary = new Task("1234567890","20character TaskName","Bring the utmost frustrating item for Greg Davies.");
	}
	
	/*
	 * Clears out the database so each test will have a fresh start.
	 */
	@BeforeEach
	@DisplayName("Clean up the database")
	void clearDatabase() {
		taskService.getDatabase().clear();
	}
	
	/*
	 * Tests if the task service object can be created successfully. 
	 */
	@Test
	@DisplayName("Check Task Service can be created.")
	void testCreationSuccess() {
		assertNotNull(taskService);
	}
	
	/*
	 * Tests if the addTask function successfully adds a new task into the database.
	 * Attempts to add each known, good task into the database, and then confirms
	 * each task indeed exists inside of the database.
	 */
	@Test
	@DisplayName("Check Tasks can be added.")
	void testAddTask() {
		assertTrue(taskService.addTask(task));
		assertEquals(taskService.getTask(task.getId()), task);
		
		assertTrue(taskService.addTask(lowerBoundary));
		assertEquals(taskService.getTask(lowerBoundary.getId()), lowerBoundary);
		
		assertTrue(taskService.addTask(upperBoundary));
		assertEquals(taskService.getTask(lowerBoundary.getId()), lowerBoundary);
	}
	
	/*
	 * Tests if the deleteTask function truly removes a task from the task database.
	 * This creates a new task, adds the task to the database, attempts to delete the task
	 * from the database, then confirms that the task no longer exists.
	 */
	@Test
	@DisplayName("Check tasks can be deleted.")
	void testDeleteTask() {

		assertTrue(taskService.addTask(task));
		assertTrue(taskService.deleteTask("12345"));

		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				()-> {
					taskService.getTask(task.getId());
				});
		assertTrue(thrown.getMessage().equals("Task not found."));
		
	}

	/*
	 * Tests if the updateTaskName function truly updates the task name using the taskID.
	 * This adds a known, good task to the database, attempts to update the task
	 * using the updateTaskName function, then checks that the name of the task matches the 
	 * updated task values.
	 */
	@Test
	@DisplayName("Check task names can be updated.")
	void testUpdateTaskName() {
		
		assertTrue(taskService.addTask(task));

		String updatedName = "Team Task";
		assertTrue(taskService.updateTaskName("12345", updatedName));
		assertTrue(taskService.getTask("12345").getName() == "Team Task");
	}
	
	/*
	 * Tests if the updateTaskDescription function truly updates task description using the taskID.
	 * This adds a known, good task to the database, attempts to update the task using the updateTaskDescription 
	 * function, then checks that the description of the task matches the updated task values.
	 */
	@Test
	@DisplayName("Check task descriptions can be updated.")
	void testUpdateTaskDescription() {
		
		assertTrue(taskService.addTask(task));

		String updatedDescription = "Accurately describe Alex Horne\'s outfit";
		assertTrue(taskService.updateTaskDescription("12345", updatedDescription));
		assertTrue(taskService.getTask("12345").getDescription() == "Accurately describe Alex Horne\'s outfit");
	}
	
	/*
	 * Failure parameters for attempting to add a task with bad data.
	 * These lines also include a failure tag at the end to be compared with generated
	 * Illegal Argument Exception messages to make sure each parameter fails for the reason
	 * we expect. This is explained more in-depth below.
	 */
	@CsvSource({
		",Prize Task,Bring the most frustrating item for Greg Davies,ID",							// null ID
		"12345678910,Prize Task,Bring the most frustrating item for Greg Davies,ID",				// ID too long (11-character boundary)
		"123456,,Bring the most frustrating item for Greg Davies,Name",								// null task name
		"123456,The Biggest Prize Task Ever,Bring the most frustrating item for Greg Davies,Name",	// task name too long (21-character boundary)
		"123456,Prize Task,,Description",															// null description
		"123456,Prize Task,Bring the utmost frustrating thing for Greg Davies.,Description",		// description too long (51-character boundary)
	})
	
	// Test for attempting to add tasks with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check add task failures.")
	void testAddFailure(String taskId, String taskName, String taskDescription, String failureTag) {
		
		// First the test adds a known, good task to the task database.	
		assertTrue(taskService.addTask(task));
		
		// Next, it attempts to re-add the task to the database, resulting in a failure
		assertFalse(taskService.addTask(task));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to add a new task with bad data.
		 * These failure parameters are pulled from the csvsource above, and each failure
		 * throws an exception unique to the failure (a bad name will list "Name not valid," etc.). 
		 * Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {taskService.addTask(new Task(taskId,taskName,taskDescription));}
				);
		
		/*
		 * Each failure type has an associated tag in the csv source, and that tag is concatenated 
		 * with the phrase " not valid." to produce another failure message. If the failure message 
		 * generated by the real Illegal Argument Exception matches the concatenated message, it 
		 * means the expected failure occurred and the task addition failed.
		 */
		assertTrue(thrown.getMessage().equals("Task " + failureTag + " not valid."));
		
		/*
		 * Finally, we check our that our database does not contain any entry with an ID that matches
		 * the bad task's ID, meaning the new task with bad data was truly not added.
		 */
		Assertions.assertThrows(IllegalArgumentException.class, 
				()-> {taskService.getTask(taskId);}
				);
	}	
	
	/*
	 * Test attempts to delete a task that does not exist. A known, good task
	 * is added to the database, we check that it exists, then we try to delete a task
	 * using a taskId that does not match the existing contact. Then we check to make sure
	 * the known, good task still exists within our database.
	 */
	@Test
	@DisplayName("Check delete task failures.")
	void testDeleteFailure() {
		
		assertTrue(taskService.addTask(task));
		
		/* 
		 * Attempts to delete a task that does not exist, which results in the error message
		 * "Task not found." This error message is assigned the name "thrown", and compared 
		 * with the expected error message string below.
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {
					taskService.deleteTask("54321");
					});

		assertTrue(thrown.getMessage().equals("Task not found."));
		
		assertTrue(taskService.getTask("12345") == task);
	}
	
	/*
	 * Failure parameters for attempting to update an existing task name with bad data.
	 */
	@CsvSource({
		"12345,,New name not valid.",								// null task name
		"12345,21 character TaskName, New name not valid.",			// task name too long (21-character boundary)
		"54321,20character TaskName,Task not found."				// taskId does not exist
	})
	
	// Test for attempting to update tasks with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check update task name failures.")
	void testUpdateNameFailure(String taskId, String taskName, String failureMessage) {
		
		// First, the test adds a known, good task to the database.		
		assertTrue(taskService.addTask(task));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to update the known, good task with bad data.
		 * These failure parameters are pulled from the csvsource above, and throws an exception
		 * that reads "New name not valid". 
		 * Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {
					taskService.updateTaskName(taskId, taskName);
					});
		
		/* 
		 * If the failure message generated by the real Illegal Argument Exception matches the failure
		 * message in our parameters, it  means the expected failure occurred and the data update failed.
		 */
		assertTrue(thrown.getMessage().equals(failureMessage));
		
		/*
		 * Finally, we check our existing, good task to make sure it remains unchanged in the
		 * database, confirming that no bad data was added to that task.
		 */
		
		assertTrue(taskService.getTask("12345") == task);
	}	
	
	/*
	 * Failure parameters for attempting to update an existing task with a bad description.
	 */
	@CsvSource({
		"12345,,New description not valid.",															// null description
		"12345,Bring the utmost frustrating thing for Greg Davies.,New description not valid.",			// description too long (51-character boundary)
		"54321,Bring the most enchanting item.,Task not found."											// taskId does not exist
	})
	
	// Test for attempting to update task descriptions with bad data. Explained below.
	@ParameterizedTest
	@DisplayName("Check update task description failures.")
	void testUpdateDescriptionFailure(String taskId, String taskDescription, String failureMessage) {
		
		// First, the test adds a known, good task to the database.		
		assertTrue(taskService.addTask(task));
		
		/* 
		 * Since we know bad data will throw an illegal argument exception, this test first
		 * establishes a new illegal argument exception named thrown, and makes it equal to
		 * the exception thrown when we try to update the known, good task with bad data.
		 * These failure parameters are pulled from the csvsource above, and are expected to
		 * throw a failure message that reads "New description not valid". 
		 * Continued below ...
		 */
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, 
				() -> {
					taskService.updateTaskDescription(taskId, taskDescription);
					});
		
		/*
		 * If the failure message generated by the real Illegal Argument Exception matches the 
		 * message in our failing parameters, the expected failure occurred and the data update failed.
		 */
		assertTrue(thrown.getMessage().equals(failureMessage));
		
		/*
		 * Finally, we check our existing, good task to make sure it remains unchanged in the
		 * database, confirming that no bad data was added to that task.
		 */
		assertTrue(taskService.getTask("12345") == task);
	}
	
	/*
	 * Clears all data from testing variables once all tests are done.
	 */
	@AfterAll
	@DisplayName("Tear down all testing components")
	static void tearDown() {
		taskService = null;
		task = null;
		lowerBoundary = null;
		upperBoundary = null;
	}
}
