package onlineticketing.service;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encryption {
	/**
	 * Encrypt the passed-in string with MD5
	 * 
	 * @param str	the plain text
	 * @return		the encrypted text
	 */
	public static String getMD5Str(String str) throws Exception {	    
		try {	        
			MessageDigest md = MessageDigest.getInstance("MD5");	       
			md.update(str.getBytes());        
			return new BigInteger(1, md.digest()).toString(16);	    
		} catch (Exception e) {	        
			throw new Exception("MD5 encrypt error: "+e.toString());	    
		}	
	}
}
