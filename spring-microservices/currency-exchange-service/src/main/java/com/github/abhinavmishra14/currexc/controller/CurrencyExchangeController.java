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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.currexc.model.ExchangeRatesModel;
import com.github.abhinavmishra14.currexc.repository.ExchangeRatesRepository;
import com.github.abhinavmishra14.rws.exceptions.RWSException;

/**
 * The Class CurrencyExchangeController.
 */
@RestController
public class CurrencyExchangeController {
	
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
		final ExchangeRatesModel exchangeRates = repository.findByFromAndTo(from, to);
		if(exchangeRates!=null) {
			exchangeRates.setPort(
					Integer.parseInt(environment.getProperty("local.server.port")));		
			return ResponseEntity.ok(exchangeRates);
		} else {
			throw new RWSException(String.format("from '%s' and/or to %s inputs are invalid!", from, to));
		}
	}
}