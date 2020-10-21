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
package com.github.abhinavmishra14.rws.app.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.github.abhinavmishra14.rws.repository.UserRepository;
import com.github.abhinavmishra14.rws.repository.entity.UserEntity;

/**
 * The Class DatabaseLoader.
 */
@Component
@Order //Default order is lowest, hence this application runner may run at the last of initial startup
public class DatabaseLoader implements ApplicationRunner {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(final ApplicationArguments args) throws Exception {
		//Setup initial user for login. Remember to change the password at later time.
		//This code logic will run only for initial startup or when admin user is missing
		final UserEntity user = userRepository.findByUsername("admin");
		if (user == null) {
			final List<UserEntity> users = new ArrayList<UserEntity>();
			final UserEntity userOne = new UserEntity("Administrator", new Date(), "admin", "admin",
					new String[] { "ROLE_ADMIN", "ROLE_USER" });
			users.add(userOne);
			userRepository.saveAll(users);
		}
	}
}
