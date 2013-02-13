/*
 User: erez
 Date: 2/14/13
 Time: 12:50 AM
 */
package com.moshavit.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

public class BaseRepository {

	@Inject protected SessionFactory sessionFactory;

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
