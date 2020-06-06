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
package com.github.abhinavmishra14.rws.model;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Response.
 */
public class Response implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The status message. */
	private String statusMessage;
	
	/** The status code. */
	private String statusCode;
	
	/**
	 * Instantiates a new response.
	 */
	public Response() {
		super();
	}
	
	/**
	 * Instantiates a new response.
	 *
	 * @param statusMessage the status message
	 * @param statusCode the status code
	 */
	public Response(final String statusMessage, final String statusCode) {
		super();
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
	}

	/**
	 * Gets the status message.
	 *
	 * @return the status message
	 */
	public final String getStatusMessage() {
		return statusMessage;
	}
	
	/**
	 * Sets the status message.
	 *
	 * @param statusMessage the new status message
	 */
	public final void setStatusMessage(final String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	/**
	 * Gets the status code.
	 *
	 * @return the status code
	 */
	public final String getStatusCode() {
		return statusCode;
	}
	
	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 */
	public final void setStatusCode(final String statusCode) {
		this.statusCode = statusCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Response [statusMessage=" + statusMessage + ", statusCode=" + statusCode + "]";
	}
}
