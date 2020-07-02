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
package com.github.abhinavmishra14.rws.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.abhinavmishra14.rws.model.UserModel;
import com.github.abhinavmishra14.rws.utils.RWSUtils;

/**
 * The Class UserService.
 */
@Component
public class UserService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	/** The Constant USERS. */
	private static final List<UserModel> USERS = new ArrayList<> (); 
	
	static {
		//Initialize demo data for USERS list. 
		//TODO:: We will fetch the list of users from db later by integrating JPA
		final Calendar calUsr1 = Calendar.getInstance();
		//Moth starts from 0-11 (12 months), Hence set 2 for march
		calUsr1.set(1986, 1, 22); 
		USERS.add(new UserModel(1, "abhinav", calUsr1.getTime()));
		final Calendar calUsr2 = Calendar.getInstance();
		//Moth starts from 0-11 (12 months), Hence set 4 for may
		calUsr2.set(1987, 6, 11); 
		USERS.add(new UserModel(2, "veena", calUsr2.getTime()));
		final Calendar calUsr3 = Calendar.getInstance();
		//Moth starts from 0-11 (12 months), Hence set 9 for oct
		calUsr3.set(2019, 8, 25); 
		USERS.add(new UserModel(3, "reyansh", calUsr3.getTime()));
	}
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<UserModel> findAll() {
		LOGGER.info("findAll invoked..");
		return USERS;
	}
	
	/**
	 * Find one.
	 *
	 * @param id the id
	 * @return the user
	 */
	public UserModel findOne(final int id) {
		LOGGER.info("findOne invoked for input id: {}", id);
		UserModel user = null;
		for (final UserModel eachUsr : USERS) {
			if(eachUsr.getId() == id) {
				user = eachUsr;
				break;
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
	public UserModel save(final UserModel user) {
		LOGGER.info("save invoked for input user: {}", user);
		if (user != null) {
			if(user.getId() == 0) {
				user.setId(RWSUtils.generateId(4, 100));
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
		for (final Iterator<UserModel> iterator = USERS.iterator(); iterator.hasNext();) {
			final UserModel eachUsr = iterator.next();
			if(eachUsr.getId() == id) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}
}
