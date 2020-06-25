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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.abhinavmishra14.rws.model.Post;
import com.github.abhinavmishra14.rws.utils.RWSUtils;

/**
 * The Class PostDao.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostDao {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PostDao.class);

	/** The Constant POSTS. */
	private static final Map<Integer, List<Post>> POSTS = new ConcurrentHashMap<Integer, List<Post>>(); 
	
	static {
		//Initialize demo data for POSTS. 
		//TODO:: We will fetch the list of posts from db later by integrating JPA
		final Post userPost1 = new Post(101, "Hello, there", new Date());
		final Post userPost11 = new Post(102, "hi, there", new Date());
		final Post userPost12 = new Post(103, "what's up", new Date());
		final List<Post> postsFor1 = new ArrayList<Post>();
		postsFor1.add(userPost1); postsFor1.add(userPost11); postsFor1.add(userPost12);
		
		final Post userPost21 = new Post(104, "welcome friend", new Date());
		final Post userPost22 = new Post(105, "Hello, friend", new Date());
		final List<Post> postsFor2 = new ArrayList<Post>();
		postsFor2.add(userPost21); postsFor2.add(userPost22);

		final Post userPost31 = new Post(106, "hi, friend", new Date());
		final Post userPost32 = new Post(107, "Hello, there", new Date());
		
		final List<Post> postsFor3 = new ArrayList<Post>();
		postsFor2.add(userPost31); postsFor2.add(userPost32);
		
		POSTS.put(1, postsFor1); POSTS.put(2, postsFor2); POSTS.put(3, postsFor3); 

	}
	
	/**
	 * Find all.
	 *
	 * @param userId the user id
	 * @return the collection
	 */
	public List<Post> findAll(final int userId) {
	  LOGGER.info("findAll invoked for retriving the posts..");
	  return POSTS.get(userId);
	}
	
	/**
	 * Find one.
	 *
	 * @param userId the user id
	 * @param postId the post id
	 * @return the post
	 */
	public Post findOne(final int userId, final int postId) {
		LOGGER.info("findOne invoked for retriving the post for postId: {} and userId: {}", postId, userId);
		final List<Post> userPosts = POSTS.get(userId);
		Post post = null;
		if (userPosts !=null && !userPosts.isEmpty()) {
			for (final Post eachPost : userPosts) {
				if (eachPost.getIdentifier() == postId) {
					post = eachPost;
					break;
				}
			}
		}
		return post;
	}
	
	/**
	 * Save.
	 *
	 * @param userId the user id
	 * @param post the post
	 * @return the post
	 */
	public Post save(final int userId, final String post) {
		LOGGER.info("save invoked for creating the post for userId: {}", userId);
		Post newPost = null;
		if (userId > 0 && StringUtils.isNotBlank(post)) {
			final List<Post> userPosts = POSTS.get(userId);
			newPost = new Post(RWSUtils.generateId(108, 200), post, new Date());
			userPosts.add(newPost);
			POSTS.put(userId, userPosts);
		}
		return newPost;
	}
}
