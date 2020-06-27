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
package com.github.abhinavmishra14.rws.app.swagger.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * The Class SwaggerConfig.
 */
@Configuration
public class SwaggerConfig {
	
	/** The Constant DEFAULT_PRODUCES_AND_CONSUMES. */
	private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<String>(
			Arrays.asList("application/json", "application/xml"));

	/**
	 * Api.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)  
				.select()                                  
				//.apis(RequestHandlerSelectors.any())
	            .apis(RequestHandlerSelectors.basePackage("com.github.abhinavmishra14"))
				.paths(PathSelectors.any())
				//Let's be specific to what we consumer and what we produce, instead of "*/*"
				.build().consumes(DEFAULT_PRODUCES_AND_CONSUMES).produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.apiInfo(apiInfo());
	}

	/**
	 * Api info.
	 *
	 * @return the api info
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Restful webservices using spring boot")
				.description("A demo restful webservice project using spring boot")
				.version("1.0")
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
				.build();
	}
}
