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
package com.github.abhinavmishra14.rws.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.abhinavmishra14.rws.dao.PostDao;
import com.github.abhinavmishra14.rws.dao.UserDao;
import com.github.abhinavmishra14.rws.exceptions.PostNotFoundException;
import com.github.abhinavmishra14.rws.exceptions.RWSException;
import com.github.abhinavmishra14.rws.exceptions.UserNotFoundException;
import com.github.abhinavmishra14.rws.model.Post;
import com.github.abhinavmishra14.rws.model.Response;
import com.github.abhinavmishra14.rws.model.User;

/**
 * The Class PostController.
 */
@RestController
public class PostController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
	
	/** The post dao. */
	@Autowired
	private PostDao postDao;
	
	/** The user dao. */
	@Autowired
	private UserDao userDao;
	
	/**
	 * Gets the all posts for A user.
	 *
	 * @param id the id
	 * @return the all posts for A user
	 */
	@GetMapping(path = "/users/{id}/posts")
	public ResponseEntity<List<Post>> getAllPostsForAUser(@PathVariable final int id) {
		LOGGER.info("getAllPostsForAUser invoked for user id: {}", id);
		if (id > 0) {
			final User userById = userDao.findOne(id);
			if (userById == null) {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
			final List<Post> allPosts = postDao.findAll(id);
			if (allPosts != null && !allPosts.isEmpty()) {
				return ResponseEntity.ok(allPosts);
			} else {
				throw new PostNotFoundException(String.format("Posts not available for user id: %s", id));
			}
		} else {
			throw new RWSException(String.format("User id '%s' is invalid!", id));
		}
	}
	
	/**
	 * Gets the post.
	 *
	 * @param id the id
	 * @param postId the post id
	 * @return the post
	 */
	@GetMapping(path = "/users/{id}/posts/{postId}")
	public EntityModel<Post> getPostDetails(@PathVariable final int id, @PathVariable final int postId) {
		LOGGER.info("getPost invoked for user id: {} and postId: {}", id, postId);
		if (id > 0) {
			final User userById = userDao.findOne(id);
			if (userById == null) {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
			final Post post = postDao.findOne(id, postId);
			if (post != null) {
				// "all-posts-by-user", SERVER_PATH + "/users/{id}/posts"
				// getAllPostsForAUser
				final EntityModel<Post> resource = EntityModel.of(post);
				final WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllPostsForAUser(id));
				resource.add(linkTo.withRel("all-posts-by-user"));

				// HATEOAS
				return resource;
			} else {
				throw new PostNotFoundException(String.format("Post not available for user id: %s and postId: %s", id, postId));
			}
		} else {
			throw new RWSException(String.format("User id '%s' is invalid!", id));
		}
		
	}
	
	/**
	 * Creates the post.
	 *
	 * @param id the id
	 * @param post the post
	 * @return the response entity
	 */
	@PostMapping(path = "/users/{id}/posts")
	public ResponseEntity<Response> createPost(@PathVariable final int id, @RequestBody final Post post) {
		LOGGER.info("createPost invoked with payload: {} for userId: ", post, id);
		if (id > 0) {
			final User userById = userDao.findOne(id);
			if (userById == null) {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
			final Post createdPost = postDao.save(id, post.getContent());
			final Response resp = new Response("CREATED", "Post created successfully.");
			resp.setAdditionalProperty("post", createdPost);
			final URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
			return ResponseEntity.created(location).body(resp);
		} else {
			throw new RWSException(String.format("User id '%s' is invalid!", id));
		}
	}
} 
