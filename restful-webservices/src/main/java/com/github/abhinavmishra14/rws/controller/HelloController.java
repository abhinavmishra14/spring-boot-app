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
package com.github.abhinavmishra14.rws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.rws.model.Response;

/**
 * The Class HelloController.
 */
@RestController
public class HelloController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

	/**
	 * Hello.<br>
	 * Simple get example
	 *
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/hello")
	public String hello() {
		LOGGER.info("hello invoked..");
		return "Hello";
	}
	
	/**
	 * Hello.<br>
	 * Example of get method with string param
	 *
	 * @param name the name
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/sayHello")//path must be different for overloaded methods
	public String hello(@RequestParam final String name) {
		LOGGER.info("hello invoked with param: {}", name);
		return "Hello "+name;
	}
	
	/**
	 * Hello via get mapping.<br>
	 * Example of get method with @GetMapping annotation. Using this annotation we
	 * would not require @RequestMapping and method param for this annotation
	 *
	 * @param name the name
	 * @return the string
	 */
	@GetMapping(path = "/sayHelloAgain")
	public String helloViaGetMapping(@RequestParam final String name) {
		LOGGER.info("helloViaGetMapping invoked with param: {}", name);
		return "Hello "+name;
	}
	
	/**
	 * Hello bean via get mapping.<br>
	 * Example of get implementation which returns a bean/object as response in json format based on input param
	 * Json format as response is default format.
	 *
	 * @param name the name
	 * @return the response
	 */
	@GetMapping(path = "/sayHelloBean")
	public Response helloBeanViaGetMapping(@RequestParam final String name) {
		LOGGER.info("helloBeanViaGetMapping invoked with param: {}", name);
		final Response response = new Response();
		response.setStatusCode("SUCCESS"); 
		response.setStatusMessage("Hello "+name);
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
	@GetMapping(path = "/sayHelloBean/pathvariable/{name}")
	public Response helloBeanViaGetMappingPathVariable(@PathVariable final String name) {
		LOGGER.info("helloBeanViaGetMappingPathVariable invoked with param: {}", name);
		final Response response = new Response();
		response.setStatusCode("SUCCESS"); 
		response.setStatusMessage(String.format("Hello %s , Hope you are doing well!", name));
		return response;
	}
}
