package onlineticketing.onlineticketing;

import javax.servlet.http.HttpSession;

public class Session {
	
	private static final String PERMISSION = "permission";
	private static final String USERID = "userid";
	
	private HttpSession httpSession = null;
	private static Session session = null;
	
	/**
	 * Get the instance of a session.
	 */
	public static Session getInstance() {
		if(session == null)
			session = new Session();
		return session;
	}
	
	/**
	 * Create a new session and keep it for 24 hours.
	 * 
	 * @param httpSession
	 * @param permission
	 */
	public void createSession(HttpSession httpSession, 
			int permission, int userid) {
		if (session.httpSession == null) {
			httpSession.setAttribute(PERMISSION, permission);
			httpSession.setAttribute(USERID, userid);
			// keep session for 24 hours
			httpSession.setMaxInactiveInterval(24 * 60 * 60);
			session.httpSession = httpSession;
		}
	}
	
	/**
	 * Clear the passed-in session.
	 * 
	 * @param httpSession
	 */
	public void closeSession() {
		if (session.httpSession != null)
			session.httpSession = null;
	}
	
	/**
	 * Get the permission stored in the session.
	 * If there is no permission in the session, set the permission
	 * as invalid permission.
	 * 
	 * @return	the permission stored in the session
	 */
	public int getPermission() {
		if (session.httpSession == null) {
			return Params.INVALID_PERMISSION;
		}
		return (int) session.httpSession.getAttribute(PERMISSION);
	}
	
	/**
	 * Get the userid stored in the session.
	 * 
	 * @return	the userid stored in the session
	 */
	public int getUserid() {
		return (int) session.httpSession.getAttribute(USERID);
	}
}
