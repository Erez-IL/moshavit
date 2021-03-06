/*
 User: erez
 Date: 1/30/13
 Time: 11:01 PM
 */
package com.moshavit.persistence;

import com.moshavit.model.User;
import com.moshavit.services.PasswordUtil;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
@Transactional
public class UserRepository extends BaseRepository {
private PasswordUtil passwordUtil = new PasswordUtil();
	public Collection<User> getUsers() {
		return getSession().createQuery("from com.moshavit.model.User").list();
	}

	public User getUser(Long id) {
		checkNotNull(id, "Cannot return  a User with null ID.");
		return (User) getSession().get(User.class, id);
	}

	public Long addUser(User user) {
		checkNotNull(user.getUsername().isEmpty() ? null : user, "Cannot add a null user.");
		user.setPassword(passwordUtil.encrypt(user.getPassword()));
		user.setDateOfIssue(DateTime.now());
		getSession().saveOrUpdate(user);
		return user.getId();
	}

	public boolean passwordOK(User user,String password){
		return passwordUtil.passwordMatches(user.getPassword(),password);
	}

	public Long saveUser(User user) {
		checkNotNull(user.getUsername().isEmpty() ? null : user, "Cannot save user with no user ID.");
		user.setDateOfLastUpdate(DateTime.now());
		user.setDateOfIssue(getUser(user.getId()).getDateOfIssue());
		getSession().merge(user);
		return user.getId();
	}

	public User getUserByUsername(String username) {
		checkNotNull(username, "Cannot fetch a user with a null username.");
		return (User) getSession().createCriteria(User.class).add(Restrictions.eq("username", username)).uniqueResult();
	}

	public boolean isUsernameAvailable(String username) {
		checkNotNull(username, "Cannot check availability of a null username.");
		Query query = getSession().createQuery("select count(*) from com.moshavit.model.User where username = :username");
		query.setParameter("username", username);
		Long count = (Long) query.uniqueResult();
		return count == 0;
	}
}
