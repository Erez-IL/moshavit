/*
 User: erez
 Date: 1/22/13
 Time: 12:29 AM
 */
package com.moshavit.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class UserTest {

	ObjectMapper jsonMapper = new ObjectMapper();

	@Test
	public void createUserFromJSON() throws IOException {
		User user = jsonMapper.readValue("{\"firstName\": \"Moshe\", \"lastName\": \"Zuchmir\"} ", User.class);
		Assert.assertEquals("Moshe", user.getFirstName());
	}
}
