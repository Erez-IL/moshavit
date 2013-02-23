/*
 User: Ophir
 Date: 24/02/13
 Time: 00:27
 */
package com.moshavit.services;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PasswordUtilTest {

	PasswordUtil passwordUtil = new PasswordUtil();

	@Test
	public void hashAndCompare() {
		String password = "should work fine";
		String hashed = passwordUtil.encrypt(password);
		assertFalse(passwordUtil.passwordMatches(hashed, "should not work at all"));
		assertTrue(passwordUtil.passwordMatches(hashed, password));
	}
}
