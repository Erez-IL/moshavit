/*
 User: Ophir
 Date: 25/12/12
 Time: 23:00
 */
package com.moshavit.services;

import com.moshavit.framework.StandardAnnotatedSpringConfiguration;
import com.moshavit.model.User;
import com.moshavit.persistence.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {StandardAnnotatedSpringConfiguration.class})

public class UserServiceTest {

	UserRepository repository = mock(UserRepository.class);
	UserService userService = new UserService(repository, mock(CurrentUserService.class));

	@Test
	public void createNewUser() {
		User savedUser = new User();
		savedUser.setFirstName("Moshe");
		savedUser.setUsername("moshe");
		userService.createNewUser(savedUser);
		Mockito.verify(repository).addUser(savedUser);
	}
}
