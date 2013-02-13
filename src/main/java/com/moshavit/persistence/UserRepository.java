/*
 User: erez
 Date: 1/30/13
 Time: 11:01 PM
 */
package com.moshavit.persistence;

import com.google.common.base.Preconditions;
import com.moshavit.model.User;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
@Transactional
public class UserRepository extends BaseRepository{

	public Collection<User> getUsers() {
		return getSession().createQuery("from com.moshavit.model.User").list();
	}

	public User getUser(Long id) {
		return (User) getSession().get(User.class, id);
	}

	public Long addUser(User user) {
		checkNotNull(user, "Cannot add a null user.");
		getSession().saveOrUpdate(user);
		return user.getId();
	}

	public void saveUser(User user) {
		checkNotNull(user.getId(), "Cannot save user with no user ID.");
		getSession().merge(user);
	}

	public boolean isUsernameAvailable(String username) {
		checkNotNull(username, "Cannot check availability of a null username.");
		Query query = getSession().createQuery("select count(*) from com.moshavit.model.User where username = :username");
		query.setParameter("username", username);
		Long count = (Long) query.uniqueResult();
		return count == 0;
	}
}
