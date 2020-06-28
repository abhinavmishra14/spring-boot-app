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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class StaticFilteringModel.
 */
@JsonIgnoreProperties(value= {"propThree"}) //DO NOT INCLUDE THIS PROPERTY IN RESPONSE
public class StaticFilteringModel {
	
	/** The prop one. */
	@JsonIgnore //DO NOT INCLUDE THIS PROPERTY IN RESPONSE
	private String propOne;
	
	/** The prop two. */
	private String propTwo;
	
	/** The prop three. */
	private String propThree;
	
	/**
	 * Instantiates a new static filtering model.
	 */
	public StaticFilteringModel() {
		super();
	}
	
	/**
	 * Instantiates a new static filtering model.
	 *
	 * @param propOne the prop one
	 * @param propTwo the prop two
	 * @param propThree the prop three
	 */
	public StaticFilteringModel(final String propOne, final String propTwo, final String propThree) {
		super();
		this.propOne = propOne;
		this.propTwo = propTwo;
		this.propThree = propThree;
	}
	
	/**
	 * Gets the prop one.
	 *
	 * @return the prop one
	 */
	public String getPropOne() {
		return propOne;
	}
	
	/**
	 * Sets the prop one.
	 *
	 * @param propOne the new prop one
	 */
	public void setPropOne(final String propOne) {
		this.propOne = propOne;
	}
	
	/**
	 * Gets the prop two.
	 *
	 * @return the prop two
	 */
	public String getPropTwo() {
		return propTwo;
	}
	
	/**
	 * Sets the prop two.
	 *
	 * @param propTwo the new prop two
	 */
	public void setPropTwo(final String propTwo) {
		this.propTwo = propTwo;
	}
	
	/**
	 * Gets the prop three.
	 *
	 * @return the prop three
	 */
	public String getPropThree() {
		return propThree;
	}
	
	/**
	 * Sets the prop three.
	 *
	 * @param propThree the new prop three
	 */
	public void setPropThree(final String propThree) {
		this.propThree = propThree;
	}
}
