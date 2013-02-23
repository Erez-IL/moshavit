/*
 User: erez
 Date: 2/23/13
 Time: 2:12 PM
 */
package com.moshavit.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class BoardMessageTest {

	ObjectMapper jsonMapper = new ObjectMapper();

	@Test
	public void createMessageFromJSON() throws IOException {
		BoardMessage message = jsonMapper.readValue("{\"text\": \"someshit\"} ", BoardMessage.class);
		Assert.assertEquals("someshit",message.getMessageText());
	}
}

