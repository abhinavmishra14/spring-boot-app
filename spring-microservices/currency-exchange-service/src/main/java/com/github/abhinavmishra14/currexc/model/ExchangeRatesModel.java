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
package com.github.abhinavmishra14.currexc.model;

import java.math.BigDecimal;

/**
 * The Class ExchangeRatesModel.
 */
public class ExchangeRatesModel {

	/** The from. */
	private String from;
	
	/** The to. */
	private String to;

	/** The conversion multiple. */
	private BigDecimal conversionMultiple;

	/** The port. */
	private int port;
	
	/** The minimum limit. */
	private BigDecimal minimumLimit;
	
	/** The maximum limit. */
	private BigDecimal maximumLimit;
	
	/**
	 * Instantiates a new exchange rates model.
	 */
	public ExchangeRatesModel() {
		super();
	}

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public void setFrom(final String from) {
		this.from = from;
	}

	/**
	 * Gets the to.
	 *
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * Sets the to.
	 *
	 * @param to the new to
	 */
	public void setTo(final String to) {
		this.to = to;
	}

	/**
	 * Gets the conversion multiple.
	 *
	 * @return the conversion multiple
	 */
	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	/**
	 * Sets the conversion multiple.
	 *
	 * @param conversionMultiple the new conversion multiple
	 */
	public void setConversionMultiple(final BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(final int port) {
		this.port = port;
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

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ExchangeRatesModel [from=" + from + ", to=" + to + ", conversionMultiple=" + conversionMultiple
				+ ", port=" + port + ", minimumLimit=" + minimumLimit + ", maximumLimit=" + maximumLimit + "]";
	}
}
