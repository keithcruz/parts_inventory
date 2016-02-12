package Models;

public class User {
	
	private String name;
	private String email;
	
	/**
	 * No arg constructor that sets name and email to empty String.
	 */
	public User() {
		this.name = "";
		this.email = "";
	}
	
	/**
	 * Constructor takes a String for the name field.
	 * @param name
	 */
	public User(String name) {
		this.name = name;
		this.email = "";
	}
	
	/**
	 * Constructor takes a string for the name and the email.
	 * @param name
	 * @param email
	 */
	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	
	/**
	 * getName method returns the User name
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * set the name of the User
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * getEmail returns the User email
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * set the email of the User
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
