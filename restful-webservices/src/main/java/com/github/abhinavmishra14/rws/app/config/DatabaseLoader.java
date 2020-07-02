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
import org.springframework.stereotype.Component;

import com.github.abhinavmishra14.rws.repository.PostRepository;
import com.github.abhinavmishra14.rws.repository.UserRepository;
import com.github.abhinavmishra14.rws.repository.entity.PostEntity;
import com.github.abhinavmishra14.rws.repository.entity.UserEntity;

/**
 * The Class DatabaseLoader.
 */
@Component
public class DatabaseLoader implements ApplicationRunner {

	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	/** The post repository. */
	@Autowired
	private PostRepository postRepository;
	
	/* (non-Javadoc)
	 * @see org.springframework.boot.ApplicationRunner#run(org.springframework.boot.ApplicationArguments)
	 */
	@Override
	public void run(final ApplicationArguments args) throws Exception {
		final List<UserEntity> users = new ArrayList<UserEntity>();
		final UserEntity userOne = new UserEntity("Administrator", new Date(), "admin", "admin",
				new String[] { "ROLE_ADMIN", "ROLE_USER" });		
		final UserEntity userTwo = new UserEntity("Test User", new Date(), "testUser", "welcome",
				new String[] { "ROLE_USER" });		
		users.add(userOne); users.add(userTwo);
		userRepository.saveAll(users);

		final List<PostEntity> posts = new ArrayList<PostEntity>();
		final PostEntity postOne = new PostEntity("Hello", new Date());
		final PostEntity postOne1 = new PostEntity("Hello there", new Date());
		final PostEntity postOne2 = new PostEntity("Hi how are you", new Date());
		postOne.setUser(userOne); postOne1.setUser(userOne); postOne2.setUser(userOne);
		posts.add(postOne); posts.add(postOne1); posts.add(postOne2); 
		
		final PostEntity postTwo = new PostEntity("Hi There", new Date());
		postTwo.setUser(userTwo);
		posts.add(postTwo);
		postRepository.saveAll(posts);
	}
}
