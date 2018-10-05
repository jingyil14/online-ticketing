package onlineticketing.service;

import java.util.Random;

import onlineticketing.datasource.IdentityMap;
import onlineticketing.datasource.UserMapper;
import onlineticketing.domain.User;
import onlineticketing.onlineticketing.Params;

public class AuthenticationService {

	/**
	 * Get the permission of a user with input username and password
	 * @param username  the input username
	 * @param password  the input password
	 * @return the permission of the user with input username and password
	 */
	public int getPermission(String username, String password) {
		try {
			password = Encryption.getMD5Str(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user = UserMapper.findUser(username, password);
		if (user == null)
			return Params.INVALID_PERMISSION;
		else
			return user.getPermission();
	}
	
	public boolean registerAccount(String username, String password, int phoneNumber) {
		try {
			password = Encryption.getMD5Str(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(UserMapper.findUserByUsername(username) == null) {
			int userId = getNextUserId();
			User user = new User(userId, username, password, phoneNumber, true);
			new UserMapper().insert(user);
			return true;
		} else
			return false;
	}
	
	/**
	 * Generate a random int from 0 to 999999999 as the id for the new account,
	 * generate the new id until it does not already exist as an account id
	 * @return the next user id
	 */
	private int getNextUserId() {
		UserMapper.findAllUsers();
		
		User targetUser = new User();
		IdentityMap<User> userMap = IdentityMap.getInstance(targetUser);
		
		Random random = new Random();
		int userId = random.nextInt(Params.MAX_USER_ID);
		
		while(userMap.get(Integer.toString(userId))!=null)
			userId = random.nextInt(Params.MAX_USER_ID);
		
		return userId;
	}
}
