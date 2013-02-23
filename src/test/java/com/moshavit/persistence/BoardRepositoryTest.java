/*
 User: erez
 Date: 2/23/13
 Time: 1:53 PM
 */
package com.moshavit.persistence;

import com.moshavit.framework.StandardAnnotatedSpringConfiguration;
import com.moshavit.model.BoardMessage;
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
public class BoardRepositoryTest {

	@Inject
	private SessionFactory sessionFactory;
	@Inject BoardRepository boardRepository;

	@Test
	@Transactional
	public void addBoardMessage() {
		BoardMessage message = new BoardMessage();
		User tempUser = new User();
		tempUser.setUsername("Erez");
		message.setMessageText("ErezTest");
		message.setAuthor(tempUser);
		boardRepository.addBoardMessage(message);
		BoardMessage fetchedMessage = (BoardMessage) sessionFactory.getCurrentSession().get(BoardMessage.class, message.getId());
		assertEquals("ErezTest", fetchedMessage.getMessageText());
		assertEquals("Erez", fetchedMessage.getAuthor().getUsername());
	}
}
