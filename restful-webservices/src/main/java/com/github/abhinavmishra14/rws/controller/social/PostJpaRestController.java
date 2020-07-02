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
package com.github.abhinavmishra14.rws.controller.social;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.abhinavmishra14.rws.exceptions.PostNotFoundException;
import com.github.abhinavmishra14.rws.exceptions.RWSException;
import com.github.abhinavmishra14.rws.exceptions.UserNotFoundException;
import com.github.abhinavmishra14.rws.model.Response;
import com.github.abhinavmishra14.rws.repository.PostRepository;
import com.github.abhinavmishra14.rws.repository.UserRepository;
import com.github.abhinavmishra14.rws.repository.entity.PostEntity;
import com.github.abhinavmishra14.rws.repository.entity.UserEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class PostJpaRestController.
 */
@RestController
public class PostJpaRestController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PostJpaRestController.class);
	
	/** The post repository. */
	@Autowired
	private PostRepository postRepository;
	
	/** The user repository. */
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * Gets the all posts for A user.
	 *
	 * @param id the id
	 * @return the all posts for A user
	 */
	@Operation(summary = "Get the user's posts using userId")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
	}))
	@GetMapping(path = "/jpa/users/{id}/posts", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<List<PostEntity>> getAllPostsForAUser(@PathVariable final long id) {
		LOGGER.info("getAllPostsForAUser invoked for user id: {}", id);
		if (id > 0) {
			final Optional<UserEntity> userById = userRepository.findById(id);
			if (!userById.isPresent()) {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
			final UserEntity user = userById.get();
			final List<PostEntity> allPosts = user.getPosts();
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
	@Operation(summary = "Gets the user's post details using userId and postId")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EntityModel.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = EntityModel.class)),
	}))
	@GetMapping(path = "/jpa/users/{id}/posts/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public EntityModel<PostEntity> getPostDetails(@PathVariable final long id, @PathVariable final int postId) {
		LOGGER.info("getPost invoked for user id: {} and postId: {}", id, postId);
		if (id > 0) {
			final Optional<UserEntity> userById = userRepository.findById(id);
			if (!userById.isPresent()) {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
			final UserEntity user = userById.get();
			final List<PostEntity> allPosts = user.getPosts();
			
			PostEntity post = null;
			if (allPosts != null && !allPosts.isEmpty()) {
				for (final PostEntity eachPost : allPosts) {
					if (eachPost.getIdentifier() == postId) {
						post = eachPost;
						break;
					}
				}
			}
			if (post != null) {
				// "all-posts-by-user", SERVER_PATH + "/users/{id}/posts"
				// getAllPostsForAUser
				final EntityModel<PostEntity> resource = EntityModel.of(post);
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
	@Operation(summary = "Create post for a user using userId")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
	}))
	@PostMapping(path = "/jpa/users/{id}/posts", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> createPost(@PathVariable final long id, @RequestBody final PostEntity post) {
		LOGGER.info("createPost invoked with payload: {} for userId: ", post, id);
		if (id > 0) {
			final Optional<UserEntity> userById = userRepository.findById(id);
			if (!userById.isPresent()) {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
			if (post != null) {
				post.setUser(userById.get());
				post.setTimestamp(new Date());
				final PostEntity createdPost = postRepository.save(post);
				final Response resp = new Response("CREATED", "Post created successfully.");
				resp.setAdditionalProperty("post", createdPost);
				final URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(id).toUri();
				return ResponseEntity.created(location).body(resp);
			} else {
				throw new RWSException("Post payload cannot be empty!");
			}
		} else {
			throw new RWSException(String.format("User id '%s' is invalid!", id));
		}
	}
} 
