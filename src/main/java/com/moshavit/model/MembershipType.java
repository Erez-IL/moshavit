/*
 User: Ophir
 Date: 25/12/12
 Time: 22:48
 */
package com.moshavit.model;

public enum MembershipType {
	resident, member;

	public boolean isResident() {
		return equals(resident);
	}

	public boolean isMember(){
		return equals(member);
	}
}
