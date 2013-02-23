/*
 User: Ophir
 Date: 24/02/13
 Time: 00:25
 */
package com.moshavit.services;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

	/** Hash a password for the first time */
	public String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/** Check that an unencrypted password matches one that has previously been hashed */
	public boolean passwordMatches(String hashed, String candidate) {
		return BCrypt.checkpw(candidate, hashed);
	}
}
