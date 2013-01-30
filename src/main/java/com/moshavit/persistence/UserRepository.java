/*
 User: erez
 Date: 1/30/13
 Time: 11:01 PM
 */
package com.moshavit.persistence;

import com.google.common.collect.Maps;
import com.moshavit.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;

@Repository
public class UserRepository {

	private static Long userCounter = 0L;
	private final Map<Long, User> users = Maps.newConcurrentMap();

	public Collection<User> getUsers() {
		return users.values();
	}

	@Transactional
	public User getUser(Long id) {
		return users.get(id);
	}

	public Long addUser(User user) {
		user.setId(userCounter);
		userCounter += 1;
		users.put(user.getId(), user);
		return user.getId();
	}
}
