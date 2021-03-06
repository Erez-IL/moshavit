/*
 User: erez
 Date: 2/21/13
 Time: 7:20 PM
 */
package com.moshavit.persistence;

import com.moshavit.model.BoardMessage;
import com.moshavit.model.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

@Repository
@Transactional
public class BoardRepository extends BaseRepository {

	public Collection<BoardMessage> getBoardMessages() {
		return getSession().createQuery("from com.moshavit.model.BoardMessage").list();
	}

	public BoardMessage getBoardMessage(Long id) {
		checkNotNull(id, "Cannot return  a Message with null ID.");
		return (BoardMessage) getSession().get(BoardMessage.class, id);
	}

	public Long addBoardMessage(BoardMessage boardMessage) {
		checkNotNull(boardMessage.getMessageText().isEmpty() ? null : boardMessage, "Cannot add a null Message.");
		boardMessage.setAuthor((User) getSession().get(User.class, boardMessage.getAuthor().getId()));
		boardMessage.setDateOfIssue(DateTime.now());
		getSession().saveOrUpdate(boardMessage);
		return boardMessage.getId();
	}


	public Long updateBoardMessage(BoardMessage boardMessage) {
		checkNotNull(boardMessage.getMessageText().isEmpty() ? null : boardMessage, "Cannot add a null Message.");
		boardMessage.setDateOfIssue(getBoardMessage(boardMessage.getId()).getDateOfIssue());
		getSession().merge(boardMessage);
		return boardMessage.getId();
	}
}
