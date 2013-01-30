/*
 User: Ophir
 Date: 25/12/12
 Time: 22:53
 */
package com.moshavit.services;

import com.google.common.collect.Maps;
import com.moshavit.model.User;
import org.jboss.resteasy.spi.NotFoundException;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;

@Service
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

	private static Integer userCounter = 0;
	private final Map<String, User> users = Maps.newConcurrentMap();

	public UserService() {
		User user1 = new User();
		user1.setId("20");
		user1.setFirstName("Erez");
		users.put(user1.getId(), user1);
		User user2 = new User();
		user2.setId("21");
		user2.setFirstName("Haim");

		users.put(user2.getId(), user2);
	}

	@GET
	public Collection<User> getUsers() {
		return users.values();
	}

	@POST
	public User createNewUser(User user) {
		user.setId(userCounter.toString());
		userCounter += 1;

		users.put(user.getId(), user);
		return user;
	}

	@GET
	@Path("/{id}")
	public User getUser(@PathParam("id") String id) {
		User user = users.get(id);
		if (user == null) {
			throw new NotFoundException("No such user " + id);
		}
		return user;
	}

	// TODO erez >> add edit user details service

}
