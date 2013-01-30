/*
 User: Ophir
 Date: 25/12/12
 Time: 23:00
 */
package com.moshavit.services;

import com.moshavit.model.User;
import org.junit.Assert;
import org.junit.Test;

public class UserServiceTest {

	UserService userService = new UserService();

	@Test
	public void createNewUser() {
		User savedUser = new User();
		savedUser.setFirstName("Moshe");
		userService.createNewUser(savedUser);

		User fetchedUser = userService.getUser(savedUser.getId());
		Assert.assertEquals(savedUser.getId(), fetchedUser.getId());
		Assert.assertEquals(savedUser.getFirstName(), fetchedUser.getFirstName());
	}
}
