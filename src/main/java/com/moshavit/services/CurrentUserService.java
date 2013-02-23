/*
 User: Ophir
 Date: 24/02/13
 Time: 00:09
 */
package com.moshavit.services;

import com.moshavit.model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("session")
public class CurrentUserService {

	private User currentUser;

	public User getCurrentUser() {
		return currentUser;
	}

	public boolean isLoggedIn() {
		return currentUser != null;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
