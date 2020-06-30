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
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.abhinavmishra14.rws.dao.UserDao;
import com.github.abhinavmishra14.rws.exceptions.RWSException;
import com.github.abhinavmishra14.rws.exceptions.UserNotFoundException;
import com.github.abhinavmishra14.rws.model.Response;
import com.github.abhinavmishra14.rws.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class UserRestController.
 */
@RestController
public class UserRestController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRestController.class);

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/**
	 * Gets the all users. <br>
	 * 
	 * GET /users
	 *
	 * @return the all users
	 */
	@Operation(summary = "Gets all users")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = List.class)),
	}))
	@GetMapping(path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<User> getAllUsers() {
		LOGGER.info("getAllUsers invoked..");
		return userDao.findAll();
	}

	/**
	 * Gets the user.<br>
	 * 
	 * GET /users/{id}
	 *
	 * @param id the id
	 * @return the user
	 */
	@Operation(summary = "Get the user using userId")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EntityModel.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = EntityModel.class)),
	}))
	@GetMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public EntityModel<User> getUser(@PathVariable final int id) {
		LOGGER.info("getUser invoked for id: {}", id);
		final User userById = userDao.findOne(id);
		if (userById == null) {
			throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
		}

		// "all-users", SERVER_PATH + "/users"
		// retrieveAllUsers
		final EntityModel<User> resource = EntityModel.of(userById);
		final WebMvcLinkBuilder linkTo = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
		resource.add(linkTo.withRel("all-users"));

		// HATEOAS
		return resource;
	}

	/**
	 * Creates the users.<br>
	 * 
	 * POST /users <br>
	 * Payload : [ { "id": 4, "name": "sunny", "birthdate": "1992-02-17" }, {"name": "sunny2", "birthdate": "1992-02-18" } ] <br>
	 * Id is auto generated if not provided in request
	 *
	 * @param user the user
	 * @return the ResponseEntity<Response>
	 */
	@Operation(summary = "Creates the user")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
	}))
	@PostMapping(path = "/users", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> createUsers(@Valid @RequestBody final User user) {
		LOGGER.info("createUser invoked with payload: {}", user);
		if (user != null) {
			final User createdUsr = userDao.save(user);
			final Response resp = new Response("CREATED", "User created successfully.");
			resp.setAdditionalProperty("user", createdUsr);
			final URI location = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(user).toUri();
			return ResponseEntity.created(location).body(resp);
		} else {
			throw new RWSException(String.format("Failed to create user(s) for the given input: %s", user));
		}
	}

	/**
	 * Delete user.<br>
	 * 
	 * DELETE /users/{id}
	 *
	 * @param id the id
	 * @return the ResponseEntity<Response>
	 */
	@Operation(summary = "Deletes the user using userId")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = ResponseEntity.class)),
	}))
	@DeleteMapping(path = "/users/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Response> deleteUser(@PathVariable final int id) {
		LOGGER.info("deleteUser invoked for id: {}", id);
		if (id > 0) {
			if (userDao.delete(id)) {
				final Response resp = new Response("SUCCESS",
						String.format("User with id '%s' has been deleted successfully.", id));
				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			} else {
				throw new UserNotFoundException(String.format("User with id '%s' not found!", id));
			}
		} else {
			throw new RWSException(String.format("User id '%s' is invalid!", id));
		}
	}
}
