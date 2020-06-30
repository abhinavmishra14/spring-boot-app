/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2017. Abhinav Kumar Mishra. 
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
package com.github.abhinavmishra14.rws.controller.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.rws.model.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class HelloController.
 */
@RestController
public class HelloController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
	
	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Hello.<br>
	 * Simple get example
	 *
	 * @return the string
	 */
	@Operation(summary = "Prints hello")
	@ApiResponses(@ApiResponse(content = {
			@Content(schema = @Schema(implementation = String.class)) }))
	@RequestMapping(method = RequestMethod.GET, path = "/hello", headers = {
			        "content-type=application/json",
			        "content-type=application/xml"
			    })
	public String hello() {
		LOGGER.info("hello invoked..");
		return messageSource.getMessage("just.hello.message", null, LocaleContextHolder.getLocale());
	}
	
	/**
	 * Say Hello.<br>
	 * Example of get method with string param
	 *
	 * @param name the name
	 * @return the string
	 */
	@Operation(summary = "Says hello with input value")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class)) }))
	@RequestMapping(method = RequestMethod.GET, path = "/sayHello", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }) //path must be different for overloaded methods
	public String sayHello(@RequestParam final String name) {
		LOGGER.info("hello invoked with param: {}", name);
		return messageSource.getMessage("hello.message", new Object[] {name}, LocaleContextHolder.getLocale());
	}
	
	/**
	 * Hello via get mapping.<br>
	 * Example of get method with @GetMapping annotation. Using this annotation we
	 * would not require @RequestMapping and method param for this annotation
	 *
	 * @param name the name
	 * @return the string
	 */
	@Operation(summary = "Says hello with input 'name'")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class)) }))
	@GetMapping(path = "/sayHelloAgain", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public String helloViaGetMapping(@RequestParam final String name) {
		LOGGER.info("helloViaGetMapping invoked with param: {}", name);
		return messageSource.getMessage("hello.message", new Object[] {name}, LocaleContextHolder.getLocale());
	}
	
	/**
	 * Hello bean via get mapping.<br>
	 * Example of get implementation which returns a bean/object as response in json format based on input param
	 * Json format as response is default format.
	 *
	 * @param name the name
	 * @return the response
	 */
	@Operation(summary = "Says hello with input 'name' and returns Response")
	@ApiResponses(@ApiResponse(content = {
			   @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)),
			   @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = Response.class))
	      }
	))
	@GetMapping(path = "/sayHelloBean", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public Response helloBeanViaGetMapping(@RequestParam final String name) {
		LOGGER.info("helloBeanViaGetMapping invoked with param: {}", name);
		final Response response = new Response();
		response.setStatusCode("SUCCESS"); 
		response.setStatusMessage(messageSource.getMessage("hello.message", new Object[] {name}, LocaleContextHolder.getLocale()));
		return response;
	}
	
	/**
	 * Hello bean via get mapping path variable.<br>
	 * Example of get implementation which returns a bean/object as response in json format based on path variable
	 * Json format as response is default format.
	 *
	 * @param name the name
	 * @return the response
	 */
	@Operation(summary = "Says hello with path variable 'name' and returns Response")
	@ApiResponses(
		  @ApiResponse(responseCode = "200", description = "Ok, Hello returned",
	      content = {
			   @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class)),
			   @Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = Response.class))
	      }
	))
	@GetMapping(path = "/sayHelloBean/pathvariable/{name}", produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public Response helloBeanViaGetMappingPathVariable(@PathVariable final String name) {
		LOGGER.info("helloBeanViaGetMappingPathVariable invoked with param: {}", name);
		final Response response = new Response();
		response.setStatusCode("SUCCESS"); 
		response.setStatusMessage(messageSource.getMessage("hello.greeting.message", new Object[] {name}, LocaleContextHolder.getLocale()));
		return response;
	}
}
