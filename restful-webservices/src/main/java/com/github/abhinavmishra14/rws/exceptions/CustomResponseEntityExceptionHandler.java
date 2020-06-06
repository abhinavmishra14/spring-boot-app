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
package com.github.abhinavmishra14.rws.exceptions;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The Class CustomResponseEntityExceptionHandler.<br>
 * Example of custom response entity exception handler. We will be using it for RestfulWSException whenever it occurrs.
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);
	
	/**
	 * Provides handling for standard Spring MVC exceptions.
	 * @param excp the target exception
	 * @param request the current request
	 */
	@ExceptionHandler({ RestfulWSException.class }) //Apply to RestfulWSException
	public final ResponseEntity<Object> handleCustomException(final Exception excp, final WebRequest request) throws Exception {
		final String errMsg = excp.getMessage();
		LOGGER.info("handleCustomException invoked, exceptionMessage: {}", errMsg);
		if (excp instanceof RestfulWSException) {
			final RWSExceptionInfo excpInfo = new RWSExceptionInfo(errMsg, request.getDescription(false), new Date());
			return new ResponseEntity<Object>(excpInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			// Unknown exception, typically a wrapper with a common MVC exception as cause
			// (since @ExceptionHandler type declarations also match first-level causes):
			// We only deal with top-level MVC exceptions here, so let's rethrow the given
			// exception for further processing through the HandlerExceptionResolver chain.
			throw excp;
		}
	}
}
