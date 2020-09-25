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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class ExceptionInfo.
 */
@JsonInclude(Include.NON_NULL)
public class ExceptionInfo {

	/** The message. */
	private final String message;
	
	/** The details. */
	private final String details;
	
	/** The timestamp. */
	private final Date timestamp;
	
	/** The cause. */
	private String cause;
	
	/**
	 * Instantiates a new exception info.
	 *
	 * @param message the message
	 * @param details the details
	 * @param timestamp the timestamp
	 * @param cause the cause
	 */
	public ExceptionInfo(final String message, final String details, final Date timestamp, final String cause) {
		super();
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
		this.cause = cause;
	}
	
	/**
	 * Instantiates a new exception info.
	 *
	 * @param message the message
	 * @param details the details
	 * @param timestamp the timestamp
	 */
	public ExceptionInfo(final String message, final String details, final Date timestamp) {
		super();
		this.message = message;
		this.details = details;
		this.timestamp = timestamp;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Gets the cause.
	 *
	 * @return the cause
	 */
	public String getCause() {
		return cause;
	}
}
