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

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * The Class Post.
 */
@JsonInclude(Include.NON_NULL)
public class Post {
	
	/** The identifier. */
	private int identifier;
	
	/** The content. */
	private String content;
	
	/** The timestamp. */
	private Date timestamp;

	/**
	 * Instantiates a new post.
	 */
	public Post() {
		super();
	}

	/**
	 * Instantiates a new post.
	 *
	 * @param identifier the identifier
	 * @param content the content
	 * @param timestamp the timestamp
	 */
	public Post(final int identifier, final String content, final Date timestamp) {
		super();
		this.identifier = identifier;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	/**
	 * Gets the identifier.
	 *
	 * @return the identifier
	 */
	public int getIdentifier() {
		return identifier;
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
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
	 * Sets the identifier.
	 *
	 * @param identifier the new identifier
	 */
	public void setIdentifier(final int identifier) {
		this.identifier = identifier;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(final String content) {
		this.content = content;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(final Date timestamp) {
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Post [identifier=" + identifier + ", content=" + content + ", timestamp=" + timestamp + "]";
	}
}
