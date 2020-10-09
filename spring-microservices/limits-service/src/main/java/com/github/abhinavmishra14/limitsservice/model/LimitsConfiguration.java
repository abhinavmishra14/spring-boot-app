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

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The Class LimitsConfiguration.
 */
@Component
@ConfigurationProperties(prefix = "limits-service")
public class LimitsConfiguration {

	/** The minimum limit. */
	private BigDecimal minimumLimit;
	
	/** The maximum limit. */
	private BigDecimal maximumLimit;
	
	/**
	 * Instantiates a new limits configuration.
	 */
	public LimitsConfiguration() {
		super();
	}
	
	/**
	 * Instantiates a new limits configuration.
	 *
	 * @param minimumLimit the minimum limit
	 * @param maximumLimit the maximum limit
	 */
	public LimitsConfiguration(final BigDecimal minimumLimit, final BigDecimal maximumLimit) {
		super();
		this.minimumLimit = minimumLimit;
		this.maximumLimit = maximumLimit;
	}

	/**
	 * Gets the minimum limit.
	 *
	 * @return the minimum limit
	 */
	public BigDecimal getMinimumLimit() {
		return minimumLimit;
	}

	/**
	 * Sets the minimum limit.
	 *
	 * @param minimumLimit the new minimum limit
	 */
	public void setMinimumLimit(final BigDecimal minimumLimit) {
		this.minimumLimit = minimumLimit;
	}

	/**
	 * Gets the maximum limit.
	 *
	 * @return the maximum limit
	 */
	public BigDecimal getMaximumLimit() {
		return maximumLimit;
	}

	/**
	 * Sets the maximum limit.
	 *
	 * @param maximumLimit the new maximum limit
	 */
	public void setMaximumLimit(final BigDecimal maximumLimit) {
		this.maximumLimit = maximumLimit;
	}
}
