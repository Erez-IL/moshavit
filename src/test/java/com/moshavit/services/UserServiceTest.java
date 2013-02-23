/*
 User: Ophir
 Date: 25/12/12
 Time: 23:00
 */
package com.moshavit.services;

import com.moshavit.framework.StandardAnnotatedSpringConfiguration;
import com.moshavit.model.User;
import com.moshavit.persistence.UserRepository;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {StandardAnnotatedSpringConfiguration.class})

public class UserServiceTest {
	@Inject
		private SessionFactory sessionFactory;

	UserRepository repository = mock(UserRepository.class);
	UserService userService = new UserService(repository);

	@Test
	public void createNewUser() {
		User savedUser = new User();
		savedUser.setFirstName("Moshe");
		savedUser.setUsername("moshe");
		userService.createNewUser(savedUser);
		savedUser.setId(repository.getIdByUsername(savedUser.getUsername()));
		User fetchedUser = userService.getUser(savedUser.getId());
		Assert.assertEquals(savedUser.getId(), fetchedUser.getId());
		Assert.assertEquals(savedUser.getFirstName(), fetchedUser.getFirstName());
	}
}
