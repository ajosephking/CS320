/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: Task Service class used to store, update, and delete Task objects
 * within a hashmap.
 */

package projectOne;

// Used to store all of our contact objects
import java.util.HashMap;

public class TaskService {
	
	// Constructor for simple service object
	public TaskService() {}
	
	// HashMap to contain all input task objects
	HashMap<String, Task> taskDatabase = new HashMap<>();
	
	
	// Accessor method for testing only! Task database should remain private.
	public synchronized HashMap<String, Task> getDatabase() {
		
		return taskDatabase;
	}
	
	/*
	 * Method for retrieving an existing task from the task service database using a task ID. 
	 * Throws an illegal argument exception with a special message if the task is not found.
	 * Not part of the original requirements, but is used by our tests to verify tasks are
	 * successfully added or deleted from our task service database. Might be useful for future
	 * development of this application as well.
	 */
	public Task getTask(String taskId) throws IllegalArgumentException {
		
		if (taskDatabase.get(taskId) == null) {
			throw new IllegalArgumentException("Task not found.");
		}
		
		return taskDatabase.get(taskId);
	}

	
	/* 
	 * Method for adding a new task to the task service database. Input task
	 * is added to the database if it does not exist (returns true), otherwise it will
	 * not add the task and return false.
	 */
	public boolean addTask(Task task) {
		return taskDatabase.putIfAbsent(task.getId(), task) == null;
	}
	
	/*
	 * Method for removing a task from the task service database. Uses the getTask function
	 * to find a matching taskID in the task database first, then removes that entry from the 
	 * task database if there is a match (and returns true). Otherwise, it throws an 
	 * Illegal Argument Exception using getTask with a special message.
	 */
	public boolean deleteTask(String taskId) throws IllegalArgumentException {
		getTask(taskId);
		
		taskDatabase.remove(taskId);
		
		return true;
	}
	
	/*
	 * Method for updating an existing task's name within the task service database. 
	 * First, it checks if there is an existing task object with a matching taskID. If no such task
	 * exists within the database, it returns false. If there is indeed a matching taskID, it will
	 * set the name of the task to the new task's name, and finally return true.
	 */
	public boolean updateTaskName(String taskId, String updatedName) throws IllegalArgumentException  {
		
		getTask(taskId).setName(updatedName);
		
		return true;
	}
	
	/*
	 * Method for updating an existing task's description within the task service database. 
	 * First, it checks if there is an existing task object with a matching taskID. If no such task
	 * exists within the database, it returns false. If there is indeed a matching taskID, it will
	 * set the description of the task to the new task's description, and finally return true.
	 */
	public boolean updateTaskDescription(String taskId, String updatedDescription) throws IllegalArgumentException  {
		
		getTask(taskId).setDescription(updatedDescription);
		
		return true;
	}
}