/*
 User: Ophir
 Date: 25/12/12
 Time: 23:30
 */
package com.moshavit;

public class ApplicationException extends RuntimeException {

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}
}
