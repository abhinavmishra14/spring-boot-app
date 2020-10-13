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

import java.text.MessageFormat;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.abhinavmishra14.currexc.exceptions.CurrencyExchangeException;
import com.github.abhinavmishra14.currexc.feignproxy.CurrencyExchangeLimitProxy;
import com.github.abhinavmishra14.currexc.model.ExchangeRatesModel;
import com.github.abhinavmishra14.currexc.repository.ExchangeRatesRepository;
import com.github.abhinavmishra14.currexc.repository.entity.ExchangeRatesEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

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
	
	/** The proxy. */
	@Autowired
	private CurrencyExchangeLimitProxy proxy;
	
	/**
	 * Get the exchange rate.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the exchange rates model
	 */
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	@HystrixCommand(fallbackMethod = "getExchangeRateFallBack")
	public ResponseEntity<ExchangeRatesModel> getExchangeRate(@PathVariable final String from, @PathVariable final String to) {
		LOGGER.info("getExchangeRate invoked, 'from' value: {} and 'to' value: {}", from, to);
		final Set<Object> fromList = repository.getFromList();
		final Set<Object> toList = repository.getToList();
		final Set<Object> supportedCurrencies = Stream.concat(fromList.stream(), toList.stream()).collect(Collectors.toSet());
		LOGGER.info("Supported currencies: {}", supportedCurrencies);
		if (fromList.contains(from) && toList.contains(to)) {
			final ExchangeRatesModel exchangeRatesWithLimits = proxy.getLimitsConfigFromConfiguration();
			final ExchangeRatesEntity exchangeRates = repository.findByFromAndTo(from, to);
			exchangeRatesWithLimits.setFrom(exchangeRates.getFrom());
			exchangeRatesWithLimits.setTo(exchangeRates.getTo());
			exchangeRatesWithLimits.setConversionMultiple(exchangeRates.getConversionMultiple());
			exchangeRatesWithLimits.setPort(
					Integer.parseInt(environment.getProperty("local.server.port")));					
			return ResponseEntity.ok(exchangeRatesWithLimits);
		} else {
			throw new CurrencyExchangeException(
					String.format("from '%s' and/or to '%s' inputs are invalid, currently supported currencies are: %s",
							from, to, supportedCurrencies));
		}
	}
	
	/**
	 * Gets the exchange rate fall back.<br>
	 * Calls external api, example: https://api.exchangeratesapi.io/latest?base=USD&symbols=USD,INR
	 *
	 * @param from the from
	 * @param to the to
	 * @return the exchange rate fall back
	 */
	@GetMapping("/currency-exchange-fallback/from/{from}/to/{to}")
	public ResponseEntity<ExchangeRatesModel> getExchangeRateFallBack(@PathVariable final String from, @PathVariable final String to) {
		LOGGER.info("getExchangeRateFallBack invoked, 'from' value: {} and 'to' value: {}", from, to);
		final String fallbackEchangeRatesUri = MessageFormat.format(environment.getProperty("exchange.rate.fallback.uri"), from, from+","+to);
		LOGGER.info("invoking {} ...", fallbackEchangeRatesUri);
		final ResponseEntity<ObjectNode> response = new RestTemplate().getForEntity(fallbackEchangeRatesUri, ObjectNode.class);
		final ObjectNode exchangeRateObj = response.getBody();
		LOGGER.info("Third party exchange rate response: {}", exchangeRateObj);
		final ExchangeRatesModel exchangeRatesWithLimits = new ExchangeRatesModel();
		final JsonNode rates = exchangeRateObj.get("rates");
		exchangeRatesWithLimits.setFrom(from);
		exchangeRatesWithLimits.setTo(to);
		final JsonNode conMultiple = rates.get(to);
		exchangeRatesWithLimits.setConversionMultiple(conMultiple.decimalValue());
		exchangeRatesWithLimits.setPort(
				Integer.parseInt(environment.getProperty("local.server.port")));					
		return ResponseEntity.ok(exchangeRatesWithLimits);
	}
}