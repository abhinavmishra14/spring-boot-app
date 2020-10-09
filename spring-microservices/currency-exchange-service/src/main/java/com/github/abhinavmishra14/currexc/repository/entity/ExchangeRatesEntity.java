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
package com.github.abhinavmishra14.currexc.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Class ExchangeRatesEntity.
 */
@Entity(name = "exchange_rates")
public class ExchangeRatesEntity {

	/** The id. */
	@Id
	@Column(name = "id")
	private Long id;
	
	/** The from. */
	@Column(name="currency_from")
	private String from;
	
	/** The to. */
	@Column(name="currency_to")
	private String to;
	
	/** The conversion multiple. */
	@Column(name = "conversion_multiple")
	private BigDecimal conversionMultiple;
	
	/** The port. */
	@Column(name = "port")
	private int port;
	
	/**
	 * Instantiates a new exchange rates model.
	 */
	public ExchangeRatesEntity() {
		super();
	}
	
	/**
	 * Instantiates a new exchange rates model.
	 *
	 * @param id the id
	 * @param from the from
	 * @param to the to
	 * @param conversionMultiple the conversion multiple
	 */
	public ExchangeRatesEntity(final Long id, final String from, final String to, final BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
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
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
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
	 * Gets the conversion multiple.
	 *
	 * @return the conversion multiple
	 */
	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
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
