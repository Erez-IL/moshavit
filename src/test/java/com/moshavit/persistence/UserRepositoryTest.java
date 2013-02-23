/*
 User: Ophir
 Date: 03/02/13
 Time: 13:51
 */
package com.moshavit.persistence;

import com.moshavit.framework.StandardAnnotatedSpringConfiguration;
import com.moshavit.model.User;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {StandardAnnotatedSpringConfiguration.class})
public class UserRepositoryTest {

	@Inject
	private SessionFactory sessionFactory;
	@Inject UserRepository userRepository;

	@Test
	@Transactional
	public void addUser() {
		User user = new User();
		user.setFirstName("moshe");
		user.setUsername("moshe");
		Long userId = userRepository.addUser(user);
		User fetchedUser = (User) sessionFactory.getCurrentSession().get(User.class, userId);
		assertEquals("moshe", fetchedUser.getFirstName());
	}
}
