/*
 User: Ophir
 Date: 25/12/12
 Time: 23:29
 */
package com.moshavit;

public class SystemException extends RuntimeException {

	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
