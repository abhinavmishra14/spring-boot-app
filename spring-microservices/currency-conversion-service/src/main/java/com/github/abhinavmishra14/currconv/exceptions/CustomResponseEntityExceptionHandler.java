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
package com.github.abhinavmishra14.currconv.exceptions;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

import feign.FeignException;

/**
 * The Class CustomResponseEntityExceptionHandler.<br>
 * Example of custom response entity exception handler. We will be using it for CurrencyConversionException and FeignException whenever it occurs.
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
		CurrencyConversionException.class,
		FeignException.class
	}) 
	public final ResponseEntity<Object> handleCustomException(final Exception excp, final WebRequest request) throws Exception {
		final String errMsg = excp.getMessage();
		LOGGER.info("handleCustomException invoked, exceptionMessage: {}", errMsg);
		if (excp instanceof CurrencyConversionException || excp instanceof FeignException) {
			final ExceptionInfo excpInfo = new ExceptionInfo(errMsg, request.getDescription(false), new Date(),
					buildTextMessage(excp, StringUtils.EMPTY));
			LOGGER.error(errMsg, excp);
			return new ResponseEntity<Object>(excpInfo, HttpStatus.INTERNAL_SERVER_ERROR);
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
					buildTextMessage(argExcp, StringUtils.EMPTY));
		LOGGER.error(argExcp.getMessage(), argExcp);
		return handleExceptionInternal(argExcp, validationErrInfo, headers, status, request);
	}
	
	/**
	 * Builds the text message.
	 *
	 * @param throwable the throwable
	 * @param customMessage the custom message
	 * @return the string
	 */
	private String buildTextMessage(final Throwable throwable,
			final String customMessage) {
		final StringBuffer result = new StringBuffer(100);
		result.append("<br/> -------------------------------------------------------------");
		if(StringUtils.isNotBlank(customMessage)){
			result.append("<br/><br/> Error: ").append(customMessage);
		}
		prepareExceptionDetails(throwable, result);
		return result.toString();
	}

	/**
	 * Prepare exception details.
	 *
	 * @param throwable the throwable
	 * @param result the result
	 */
	private void prepareExceptionDetails(final Throwable throwable,
			final StringBuffer result) {
		final String timeOfFailure = new Date().toString();
		String hostName = StringUtils.EMPTY;
		String ipAddress = StringUtils.EMPTY;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException uhe) {
			hostName = "unknown";
			ipAddress = "unknown";
		}
		result.append("<br/><br/> Time of failure: ").append(timeOfFailure)
				.append("<br/><br/> Host where failure occurred: ").append(hostName).append(" (").append(ipAddress)
				.append(")");

		if (throwable == null) {
			result.append("<br/><br/> No exception was provided.");
		} else {
			result.append("<br/><br/> Root exception:").append(renderExceptionStackAsText(throwable));
		}
	}

	/**
	 * Render exception stack as text.
	 *
	 * @param throwable the throwable
	 * @return the string
	 */
	private String renderExceptionStackAsText(final Throwable throwable) {
		final StringBuffer result = new StringBuffer();
		if (throwable != null) {
			String message = throwable.getMessage();
			final Throwable cause = throwable.getCause();
			if (cause != null) {
				result.append(renderExceptionStackAsText(cause));
				result.append("<br/><br/> Wrapped by:");
			}
			
			if (message == null) {
				message = StringUtils.EMPTY;
			}
			
			result.append("<br/> ");
			result.append(throwable.getClass().getName());
			result.append(": ");
			result.append(message);
			result.append("<br/> ");
			result.append(renderStackTraceElements(throwable.getStackTrace()));
		}
		return result.toString();
	}

	/**
	 * Render stack trace elements.
	 *
	 * @param elements the elements
	 * @return the string
	 */
	private String renderStackTraceElements(final StackTraceElement[] elements) {
		final StringBuffer result = new StringBuffer();
		if (elements != null) {
			for (int each = 0; each < elements.length; each++) {
				result.append(" at " + elements[each].toString() + "<br/>");
			}
		}
		return result.toString();
	}
}
