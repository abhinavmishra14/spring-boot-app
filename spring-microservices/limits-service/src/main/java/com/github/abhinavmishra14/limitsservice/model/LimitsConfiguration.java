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
package com.github.abhinavmishra14.limitsservice.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The Class LimitsConfiguration.
 */
@Component
@ConfigurationProperties(prefix = "limits-service")
public class LimitsConfiguration {

	/** The minimum. */
	private int minimum;
	
	/** The maximum. */
	private int maximum;
	
	
	/**
	 * Instantiates a new limits configuration.
	 */
	public LimitsConfiguration() {
		super();
	}
	
	/**
	 * Instantiates a new limits configuration.
	 *
	 * @param minimum the minimum
	 * @param maximum the maximum
	 */
	public LimitsConfiguration(final int minimum, final int maximum) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
	}
	
	/**
	 * Gets the minimum.
	 *
	 * @return the minimum
	 */
	public int getMinimum() {
		return minimum;
	}
	
	/**
	 * Sets the minimum.
	 *
	 * @param minimum the new minimum
	 */
	public void setMinimum(final int minimum) {
		this.minimum = minimum;
	}
	
	/**
	 * Gets the maximum.
	 *
	 * @return the maximum
	 */
	public int getMaximum() {
		return maximum;
	}
	
	/**
	 * Sets the maximum.
	 *
	 * @param maximum the new maximum
	 */
	public void setMaximum(final int maximum) {
		this.maximum = maximum;
	}
}
