/*
 User: erez
 Date: 1/31/13
 Time: 12:06 AM
 */
package com.moshavit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "board_messages")
public class BoardMessage {

	@Id @GeneratedValue
	private Long id;

	@ManyToOne
	private User author;

	private String subject;
	private String messageText;
	@JsonIgnore
	@Column @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime dateOfIssue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public DateTime getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(DateTime dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
