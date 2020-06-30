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
package com.github.abhinavmishra14.rws.app.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * The Class SwaggerConfig.
 */
@Configuration
public class SwaggerConfig {

	/**
	 * Public api.
	 *
	 * @return the grouped open api
	 */
	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
				.group("public-api")
				.pathsToMatch("/**").packagesToScan("com.github.abhinavmishra14.rws.controller.hello",
						"com.github.abhinavmishra14.rws.controller")
				.build();
	}

	/**
	 * Spring shop open API.
	 *
	 * @return the open API
	 */
	@Bean
	public OpenAPI springShopOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Restful webservices using spring boot")
						.description("A demo restful webservice project using spring boot")
						.version("v1.0")
						.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0")));
	}
}
