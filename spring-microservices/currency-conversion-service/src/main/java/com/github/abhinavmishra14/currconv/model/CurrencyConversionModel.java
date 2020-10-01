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
package com.github.abhinavmishra14.currconv.model;

import java.math.BigDecimal;

/**
 * The Class CurrencyConversionModel.
 */
public class CurrencyConversionModel {

	/** The id. */
	private Long id;
	
	/** The from. */
	private String from;
	
	/** The to. */
	private String to;
	
	/** The conversion multiple. */
	private BigDecimal conversionMultiple;
	
	/** The quantity. */
	private BigDecimal quantity;
	
	/** The total calculated amount. */
	private BigDecimal totalCalculatedAmount;
	
	/** The port. */
	private int port;

	/**
	 * Instantiates a new currency conversion model.
	 */
	public CurrencyConversionModel() {
		super();
	}

	/**
	 * Instantiates a new currency conversion model.
	 *
	 * @param id the id
	 * @param from the from
	 * @param to the to
	 * @param conversionMultiple the conversion multiple
	 * @param quantity the quantity
	 * @param totalCalculatedAmount the total calculated amount
	 * @param port the port
	 */
	public CurrencyConversionModel(final Long id, final String from, final String to,
			final BigDecimal conversionMultiple, final BigDecimal quantity, final BigDecimal totalCalculatedAmount,
			final int port) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.quantity = quantity;
		this.totalCalculatedAmount = totalCalculatedAmount;
		this.port = port;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(final Long id) {
		this.id = id;
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
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(final BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the total calculated amount.
	 *
	 * @return the total calculated amount
	 */
	public BigDecimal getTotalCalculatedAmount() {
		return totalCalculatedAmount;
	}

	/**
	 * Sets the total calculated amount.
	 *
	 * @param totalCalculatedAmount the new total calculated amount
	 */
	public void setTotalCalculatedAmount(final BigDecimal totalCalculatedAmount) {
		this.totalCalculatedAmount = totalCalculatedAmount;
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
}
