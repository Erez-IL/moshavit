/*
 User: erez
 Date: 1/31/13
 Time: 12:06 AM
 */
package com.moshavit.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "board_messages")
public class BoardMessage {

	@GeneratedValue
	@Id private Long id;

	@ManyToOne
	private User author;

	private String text;

	@Column
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime time;

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
}
