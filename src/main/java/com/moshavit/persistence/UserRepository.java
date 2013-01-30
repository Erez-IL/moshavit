/*
 User: erez
 Date: 1/30/13
 Time: 11:01 PM
 */
package com.moshavit.persistence;

import com.google.common.collect.Maps;
import com.moshavit.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class UserRepository {

	private static Integer userCounter = 0;
	private final Map<String, User> users = Maps.newConcurrentMap();

	public Collection<User> getUsers() {
		return users.values();
	}

	public User getUser(String id) {
		return users.get(id);
	}

	public String addUser(User user) {
		user.setId(userCounter.toString());
		userCounter += 1;
		users.put(user.getId(), user);
		return user.getId();
	}
}
