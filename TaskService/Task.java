/*
 * Name: Alexander J. King
 * Date: 04/12/2026
 * Purpose: Task class file used to create task objects with a unique ID, name, and description.
 */

package projectOne;

public class Task {

	private final String taskId;
	private String taskName;
	private String taskDescription;
	
	// Constructor for our Task class.
	public Task(String taskId, String taskName, String taskDescription) {
		super();
		
		// Throws an exception if the ID is null or greater than 10 characters
		if (taskId == null || taskId.length() > 10) {
			throw new IllegalArgumentException("Task ID not valid.");
		}
		
		// Throws an exception if the name of the task is null or greater than 20 characters
		if (taskName == null || taskName.length() > 20) {
			throw new IllegalArgumentException("Task Name not valid.");
		}
		
		// Throws an exception if the task description is null or greater than 50 characters
		if (taskDescription == null || taskDescription.length() > 50) {
			throw new IllegalArgumentException("Task Description not valid.");
		}
		
		// If no exceptions are found, task is created using all passed parameters
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDescription = taskDescription;
	}
	
	// Accessor functions for Task class
	public String getId() {
		return taskId;
	}
	
	public String getName() {
		return taskName;
	}
	
	public String getDescription() {
		return taskDescription;
	}
	
	// Mutator Functions for Task Class. Note the taskID is not included so it remains immutable.
	public void setName(String l_name) {
		
		// Throws an exception if the input name is null or greater than 20 characters.
		if (l_name == null || l_name.length() > 20) {
			throw new IllegalArgumentException("New name not valid.");
		}
		
		this.taskName = l_name;
	}
	
	public void setDescription(String l_description) {
		
		// Throws an exception if the input description is null or greater than 50 characters.
		if (l_description == null || l_description.length() > 50) {
			throw new IllegalArgumentException("New description not valid.");
		}
		
		this.taskDescription = l_description;
	}
}
