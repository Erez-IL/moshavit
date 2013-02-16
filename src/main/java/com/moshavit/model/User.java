/*
 User: Ophir
 Date: 25/12/12
 Time: 22:45
 */
package com.moshavit.model;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	private String firstName;
	private String lastName;
	private MembershipType membership;

	@Column(unique = true)
	private String username;

	private String password;
	private String address;
	private String phoneNumber;
	private String dateOfIssue;
	private String dateOfLastUpdate;

	public String getDateOfLastUpdate() {
		return dateOfLastUpdate;
	}

	public void setDateOfLastUpdate(String dateOfLastUpdate) {
		this.dateOfLastUpdate = dateOfLastUpdate;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public MembershipType getMembership() {
		return membership;
	}

	public void setMembership(MembershipType membership) {
		this.membership = membership;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return Long.toString(id);
	}
}
