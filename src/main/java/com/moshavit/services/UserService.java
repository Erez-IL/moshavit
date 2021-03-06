/*
 User: Ophir
 Date: 25/12/12
 Time: 22:53
 */
package com.moshavit.services;

import com.moshavit.model.User;
import com.moshavit.persistence.UserRepository;
import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Service
@Path("/users")
@Scope("session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserService {

	private final UserRepository repository;
	private final CurrentUserService currentUserService;

	@Inject
	public UserService(UserRepository repository, CurrentUserService currentUserService) {
		this.repository = repository;
		this.currentUserService = currentUserService;
	}

	@POST
	@Path("/login")
	@Encoded
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void login(@FormParam("username") String username, @FormParam("password") String password) {
		User user = repository.getUserByUsername(username);
		if (user != null && repository.passwordOK(user, password)) {
			currentUserService.setCurrentUser(user);
		} else {
			throw new UnauthorizedException("Failed login");
		}
	}

	@GET
	@Path("/login")
	public User currentUser() {
		if (currentUserService.isLoggedIn()) {
			return currentUserService.getCurrentUser();
		}
		return null;
	}

	@GET
	@Path("/logout")
	public void logout() {
		currentUserService.setCurrentUser(null);
	}

	@GET
	public Collection<User> getUsers() {
		return repository.getUsers();
	}

	@GET
	@Path("/{id}")
	public User getUser(@PathParam("id") Long id) {
		User user = repository.getUser(id);
		if (user == null) {
			throw new NotFoundException("No such user " + id);
		}
		return user;
	}

	@GET
	@Path("/exist/{username}")
	public boolean isUsernameAvailable(@PathParam("username") String username) {
		return repository.isUsernameAvailable(username);
	}

	@POST
	public Long createNewUser(User user) {
		return repository.addUser(user);
	}

	@PUT
	@Path("/{id}")
	public Long updateUser(User user, @PathParam("id") Long id) {
		user.setId(id);
		return repository.saveUser(user);
	}
}
