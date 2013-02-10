/*
 User: Ophir
 Date: 03/02/13
 Time: 13:51
 */
package com.moshavit.persistence;

import com.moshavit.framework.persistence.PersistenceConfiguration;
import com.moshavit.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= PersistenceConfiguration.class)
public class UserRepositoryTest {

	@Inject
	private SessionFactory sessionFactory;

	@Test
	@Transactional
	public void updateAccount() {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from User user where user.id=:id").setInteger("id", 1);
		User user = (User) query.uniqueResult();
		user.setFirstName("moshe");
	}
}
