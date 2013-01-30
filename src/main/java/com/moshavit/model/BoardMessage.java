/*
 User: erez
 Date: 1/31/13
 Time: 12:06 AM
 */
package com.moshavit.model;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class BoardMessage {

	@Id private Long id;

	@ManyToOne @JoinColumn(name = "userId")
	private User author;

	private String text;
	private DateTime time;

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
