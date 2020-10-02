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
package com.github.abhinavmishra14.currconv.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.currconv.exceptions.CurrencyConversionException;
import com.github.abhinavmishra14.currconv.feignproxy.CurrencyConversionProxy;
import com.github.abhinavmishra14.currconv.model.CurrencyConversionModel;

/**
 * The Class CurrencyConversionController.
 */
@RestController
public class CurrencyConversionController {
	
	/** The environment. */
	@Autowired
	private Environment environment;
	
	/** The curr proxy. */
	@Autowired
	private CurrencyConversionProxy currProxy;
	
	/**
	 * Gets the exchange rate.<br>
	 * To talk to currency exchange service we will use Feign proxy. <br>
	 * Feign is a declarative HTTP client developed by Netflix.
	 * Feign aims at simplifying HTTP API clients. Simply put, the developer needs only to declare and annotate an interface while the actual implementation is provisioned at runtime.
	 * 
	 * @param from the from
	 * @param to the to
	 * @param quantity the quantity
	 * @return the exchange rate
	 */
	@GetMapping("/currency-converter/from/{from}/to/{to}/amount/{amount}")
	public ResponseEntity<CurrencyConversionModel> getExchangeRate(@PathVariable final String from, @PathVariable final String to,
			@PathVariable final BigDecimal amount) {
		final ResponseEntity<CurrencyConversionModel> currConvResp = currProxy.getExchangeRate(from, to);
		if (currConvResp.getStatusCodeValue() == 200 && null != currConvResp.getBody()) {			
			final CurrencyConversionModel model = currConvResp.getBody();
			model.setQuantity(amount);
			//Set port where currency conversion service is running.
			model.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
			model.setTotalCalculatedAmount(amount.multiply(model.getConversionMultiple()));
			return ResponseEntity.ok(model);
		} else {
			throw new CurrencyConversionException(currConvResp.getStatusCode().getReasonPhrase());
		}
	}
}