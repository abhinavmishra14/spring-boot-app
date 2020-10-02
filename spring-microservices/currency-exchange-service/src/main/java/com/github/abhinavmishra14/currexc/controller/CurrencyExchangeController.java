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
package com.github.abhinavmishra14.currexc.controller;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.currexc.exceptions.CurrencyExchangeException;
import com.github.abhinavmishra14.currexc.model.ExchangeRatesModel;
import com.github.abhinavmishra14.currexc.repository.ExchangeRatesRepository;

/**
 * The Class CurrencyExchangeController.
 */
@RestController
public class CurrencyExchangeController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	/** The environment. */
	@Autowired
	private Environment environment;
	
	/** The repository. */
	@Autowired
	private ExchangeRatesRepository repository;
	
	/**
	 * Get the exchange rate.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the exchange rates model
	 */
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ResponseEntity<ExchangeRatesModel> getExchangeRate(@PathVariable final String from, @PathVariable final String to) {
		LOGGER.info("getExchangeRate invoked, 'from' value: {} and 'to' value: {}", from, to);
		final Set<Object> fromList = repository.getFromList();
		final Set<Object> toList = repository.getToList();
		final Set<Object> supportedCurrencies = Stream.concat(fromList.stream(), toList.stream()).collect(Collectors.toSet());
		LOGGER.info("Supported currencies: {}", supportedCurrencies);
		if (fromList.contains(from) && toList.contains(to)) {
			final ExchangeRatesModel exchangeRates = repository.findByFromAndTo(from, to);
			exchangeRates.setPort(
					Integer.parseInt(environment.getProperty("local.server.port")));		
			return ResponseEntity.ok(exchangeRates);
		} else {
			throw new CurrencyExchangeException(
					String.format("from '%s' and/or to '%s' inputs are invalid, currently supported currencies are: %s",
							from, to, supportedCurrencies));
		}
	}
}