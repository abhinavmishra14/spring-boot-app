/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2020. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.abhinavmishra14.rws.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.abhinavmishra14.rws.model.User;

/**
 * The Class UserDao.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserDao {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);

	/** The Constant USERS. */
	private static final List<User> USERS = new ArrayList<> (); 
	
	static {
		//Initialize demo data for USERS list. 
		//TODO:: We will fetch the list of users from db later by integrating JPA
		final Calendar calUsr1 = Calendar.getInstance();
		//Moth starts from 0-11 (12 months), Hence set 2 for march
		calUsr1.set(1987, 2, 14); 
		USERS.add(new User(1, "abhinav", calUsr1.getTime()));
		final Calendar calUsr2 = Calendar.getInstance();
		//Moth starts from 0-11 (12 months), Hence set 4 for may
		calUsr2.set(1986, 4, 14); 
		USERS.add(new User(2, "veena", calUsr2.getTime()));
		final Calendar calUsr3 = Calendar.getInstance();
		//Moth starts from 0-11 (12 months), Hence set 9 for oct
		calUsr3.set(2019, 9, 15); 
		USERS.add(new User(3, "reyansh", calUsr3.getTime()));
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<User> findAll() {
		LOGGER.info("findAll invoked..");
		return USERS;
	}
	
	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the user
	 */
	public User findOne(final int id) {
		LOGGER.info("findOne invoked for input id: {}", id);
		User user = null;
		for (final User eachUsr : USERS) {
			if(eachUsr.getId() == id) {
				user = eachUsr;
			}
		}
		return user;
	}
	
	/**
	 * Save.
	 *
	 * @param user the user
	 * @return the user
	 */
	public User save(final User user) {
		LOGGER.info("save invoked for input user: {}", user);
		if (user != null) {
			if(user.getId() == 0) {
				user.setId(generateId(4, 100));
				USERS.add(user);
			} else {
				USERS.add(user);
			}
		}
		return user;
	}
	
	/**
	 * Delete.
	 *
	 * @param id the id
	 * @return the true when deletion successful, false when user with given id not found
	 */
	public boolean delete(final int id) {
		LOGGER.info("delete invoked for input id: {}", id);		
		for (final Iterator<User> iterator = USERS.iterator(); iterator.hasNext();) {
			final User eachUsr = iterator.next();
			if(eachUsr.getId() == id) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Generate id.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the int
	 */
	private int generateId(final int min, final int max) {
	    final Random random = new Random();
	    return random.nextInt((max - min) + 1) + min;
	}
}
