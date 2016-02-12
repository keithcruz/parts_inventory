package Models;
import java.util.*;

public class Authenticator {
	private Session session;
	private ArrayList<String> creds;  //Store the credentials as a string seperated by commas, i.e., "myemail@gmail.com,mypassword,Production Manager"
	
	
	/**
	 * Constructor takes a user and password as args and checks if they match creds stored in ArrayList.
	 * If creds match it sets a session with the proper access rights.  If not an exception is thrown.
	 * @param user
	 * @param password
	 * @throws SecurityException
	 */
	public Authenticator( ArrayList<String> cs)
	{
		
		creds = new ArrayList<String>(cs);

	}
	
	public Session Authenticate(User user, String password )throws SecurityException {
	
		boolean pass = false;
				String role = "";
		for (int i = 0; i < this.creds.size(); i++) {
			String cred[] = this.creds.get(i).split(",");
			if (user.getEmail().equals(cred[0]) && password.equals(cred[1])) {
				pass = true;
				role = cred[2];
			}
		}
		if (pass) {
			this.session = new Session(role);
			return(session);
		}
		else {
			this.session = new Session();
			throw new SecurityException("Incorrect credentials");
			
			
		}
		
	}
	/**
	 * Return the session
	 * @return session
	 */
	public Session getSession() {
		return this.session;
	}
	
	/**
	 * Set the session
	 * @param session
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	public ArrayList<String> getCreds()
	{
		return(creds);
	}

}
