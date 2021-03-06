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
package com.github.abhinavmishra14.limitsservice.controller;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.abhinavmishra14.limitsservice.model.LimitsConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * The Class LimitsConfigController.
 */
@RestController
public class LimitsConfigController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(LimitsConfigController.class);
	
	/** The limits configuration. */
	@Autowired
	private LimitsConfiguration limitsConfiguration;

	/**
	 * Gets the limits config from configuration.
	 *
	 * @return the limits configuration
	 */
	@GetMapping("/limits")
	@HystrixCommand(fallbackMethod = "getLimitsConfigFromConfigurationFallback")
	public LimitsConfiguration getLimitsConfigFromConfiguration() {
		LOGGER.info("getLimitsConfigFromConfiguration invoked..");
		return limitsConfiguration;
	}
	
	/**
	 * Fallback get limits config from configuration.
	 *
	 * @return the limits configuration
	 */
	public LimitsConfiguration getLimitsConfigFromConfigurationFallback() {
		return new LimitsConfiguration(BigDecimal.ONE, new BigDecimal("1000"));
	}
}
