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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.github.abhinavmishra14.rws.utils.RWSUtils;

/**
 * The Class CustomResponseEntityExceptionHandler.<br>
 * Example of custom response entity exception handler. We will be using it for RestfulWSException whenever it occurs.
 */
@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomResponseEntityExceptionHandler.class);
	
	/**
	 * Provides handling for standard Spring MVC exceptions.
	 *
	 * @param excp the target exception
	 * @param request the current request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	@ExceptionHandler({ 
		RWSException.class,
		PostNotFoundException.class
	}) 
	public final ResponseEntity<Object> handleCustomException(final Exception excp, final WebRequest request) throws Exception {
		final String errMsg = excp.getMessage();
		LOGGER.info("handleCustomException invoked, exceptionMessage: {}", errMsg);
		if (excp instanceof RWSException) {
			final ExceptionInfo excpInfo = new ExceptionInfo(errMsg, request.getDescription(false), new Date(),
					RWSUtils.buildTextMessage(excp, StringUtils.EMPTY));
			LOGGER.error(errMsg, excp);
			return new ResponseEntity<Object>(excpInfo, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else if (excp instanceof PostNotFoundException) {
			final ExceptionInfo pageNFExInfo = new ExceptionInfo(errMsg, request.getDescription(false), new Date(),
						RWSUtils.buildTextMessage(excp, StringUtils.EMPTY));
			LOGGER.error(errMsg, excp);
			return new ResponseEntity<Object>(pageNFExInfo, HttpStatus.NOT_FOUND);
		} else {
			// Unknown exception, typically a wrapper with a common MVC exception as cause
			// (since @ExceptionHandler type declarations also match first-level causes):
			// We only deal with top-level MVC exceptions here, so let's re-throw the given
			// exception for further processing through the HandlerExceptionResolver chain.
			throw excp;
		}
	}
	
	//We can have multiple implementations as well for the handlers if needed
	
	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * <p>This method delegates to {@link #handleExceptionInternal}.
	 *
	 * @param argExcp the arg excp
	 * @param headers the headers to be written to the response
	 * @param status the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException argExcp,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		final BindingResult bindingResult = argExcp.getBindingResult();
		final String errMsg = String.format("Validation failed, total errors: %s", bindingResult.getErrorCount());
		final String allErrors = bindingResult.getAllErrors().toString();
		final ExceptionInfo validationErrInfo = new ExceptionInfo(errMsg, allErrors, new Date(),
					RWSUtils.buildTextMessage(argExcp, StringUtils.EMPTY));
		LOGGER.error(argExcp.getMessage(), argExcp);
		return handleExceptionInternal(argExcp, validationErrInfo, headers, status, request);
	}
}
