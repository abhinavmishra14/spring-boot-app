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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class RWSException.
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error occurred while processing the request!")
public class RWSException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8024769484081511870L;

	/** The message. */
	private final String message;
	
	/** The cause. */
	private Throwable cause;

	/**
	 * Instantiates a new user not found exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public RWSException(final String message, final Throwable cause) {
		super(message, cause);
		this.message = message;
		this.cause = cause;
	}

	/**
	 * Instantiates a new user not found exception.
	 *
	 * @param message the message
	 */
	public RWSException(final String message) {
		super(message);
		this.message = message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	@Override
	public Throwable getCause() {
		return cause;
	}	
}
