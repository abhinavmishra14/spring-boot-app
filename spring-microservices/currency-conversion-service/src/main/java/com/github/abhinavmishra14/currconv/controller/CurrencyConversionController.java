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
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.abhinavmishra14.currconv.exceptions.CurrencyConversionException;
import com.github.abhinavmishra14.currconv.feignproxy.CurrencyConversionProxy;
import com.github.abhinavmishra14.currconv.model.CurrencyConversionModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * The Class CurrencyConversionController.
 */
@RestController
public class CurrencyConversionController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	/** The curr proxy. */
	@Autowired
	private CurrencyConversionProxy currProxy;
	
	/** The environment. */
	@Autowired
	private Environment environment;
	
	/**
	 * Convert.<br>
	 * To talk to currency exchange service we will use Feign proxy. <br>
	 * Feign is a declarative HTTP client developed by Netflix.
	 * Feign aims at simplifying HTTP API clients. Simply put, the developer needs only to declare and annotate an interface while the actual implementation is provisioned at runtime.
	 * 
	 * @param from the from
	 * @param to the to
	 * @param amount the amount
	 * @return the exchange rate
	 */
	@GetMapping("/currency-converter/from/{from}/to/{to}/amount/{amount}")
	@HystrixCommand(fallbackMethod = "convertFallback")
	public ResponseEntity<MappingJacksonValue> convert(@PathVariable final String from, @PathVariable final String to,
			@PathVariable final BigDecimal amount) {
		LOGGER.info("convert invoked, 'from' value: {} , 'to' value: {} and 'amount' value: {}", from, to, amount);
		final ResponseEntity<CurrencyConversionModel> currConvResp = currProxy.getExchangeRate(from, to);
		if (currConvResp.getStatusCodeValue() == 200 && null != currConvResp.getBody()) {	
			final CurrencyConversionModel model = currConvResp.getBody();
			if ((amount.compareTo(model.getMinimumLimit()) == 0 || amount.compareTo(model.getMinimumLimit()) == 1)
					&& (amount.compareTo(model.getMaximumLimit()) == 0 || amount.compareTo(model.getMaximumLimit()) <= -1)) {
				model.setAmount(amount);
				model.setTotalCalculatedAmount(amount.multiply(model.getConversionMultiple()));
				//Filter limits and return response
				return ResponseEntity.ok(serializeAllExcept(model, "CurrencyConversionFilteredModel",
						"minimumLimit", "maximumLimit"));
			} else {
				throw new CurrencyConversionException("Invalid amount, value must be within limits min: "
						+ model.getMinimumLimit() + " | max: " + model.getMaximumLimit());				
			}
		} else {
			throw new CurrencyConversionException(currConvResp.getStatusCode().getReasonPhrase());
		}
	}
	
	/**
	 * Convert fallback.<br>
	 * Calls external api, example: https://api.exchangeratesapi.io/latest?base=USD&symbols=USD,INR
	 *
	 * @param from the from
	 * @param to the to
	 * @param amount the amount
	 * @return the response entity
	 */
	@GetMapping("/currency-converter-fallback/from/{from}/to/{to}/amount/{amount}")
	public ResponseEntity<MappingJacksonValue> convertFallback(@PathVariable final String from, @PathVariable final String to,
			@PathVariable final BigDecimal amount) {
		LOGGER.info("convertFallback invoked, 'from' value: {} , 'to' value: {} and 'amount' value: {}", from, to, amount);
		final String fallbackEchangeRatesUri = MessageFormat.format(environment.getProperty("exchange.rate.fallback.uri"), from, from+","+to);
		LOGGER.info("invoking {} ...", fallbackEchangeRatesUri);
		final ResponseEntity<ObjectNode> response = new RestTemplate().getForEntity(fallbackEchangeRatesUri, ObjectNode.class);
		final ObjectNode exchangeRateObj = response.getBody();
		LOGGER.info("Third party exchange rate response: {}", exchangeRateObj);
		final CurrencyConversionModel model = new CurrencyConversionModel();
		model.setFrom(from);
		model.setTo(to);
		model.setAmount(amount);
		final BigDecimal conversionMultiple = exchangeRateObj.get("rates").get(to).decimalValue();
		model.setConversionMultiple(conversionMultiple);
		model.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		model.setTotalCalculatedAmount(amount.multiply(conversionMultiple));
		//Filter limits and return response
		return ResponseEntity.ok(serializeAllExcept(model, "CurrencyConversionFilteredModel",
				"minimumLimit", "maximumLimit"));
	}
	
	/**
	 * Serialize all except.
	 *
	 * @param dynamicModel the dynamic model
	 * @param filteringModelName the filtering model name
	 * @param propertyArray the property array
	 * @return the mapping jackson value
	 */
	private MappingJacksonValue serializeAllExcept(final Object dynamicModel,
			final String filteringModelName, final String... propertyArray) {
		final SimpleBeanPropertyFilter propFilter = SimpleBeanPropertyFilter.serializeAllExcept(propertyArray);
		final FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filteringModelName, propFilter);
		final MappingJacksonValue mappedVal = new MappingJacksonValue(dynamicModel);
		mappedVal.setFilters(filterProvider);
		return mappedVal;
	}
}