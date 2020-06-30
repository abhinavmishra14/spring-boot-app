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
package com.github.abhinavmishra14.rws.controller.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.github.abhinavmishra14.rws.model.DynamicFilteringModel;
import com.github.abhinavmishra14.rws.model.StaticFilteringModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The Class FilteringPropertyController.
 */
@RestController
public class FilteringPropertyController {
	
	/**
	 * Gets the static filtered property.
	 *
	 * @return the static filtered property
	 */
	@Operation(summary = "Gets the static filtered property")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StaticFilteringModel.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = StaticFilteringModel.class)),
	}))
	@GetMapping(path = "/getStaticFilteredProperty", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public StaticFilteringModel getStaticFilteredProperty() {
		return new StaticFilteringModel("I am 1", "I am 2", "I am 3");
	}
	
	/**
	 * Gets the static filtered property list.
	 *
	 * @return the static filtered property list
	 */
	@Operation(summary = "Gets the static filtered property list")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = List.class)),
	}))
	@GetMapping(path = "/getStaticFilteredPropertyList", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<StaticFilteringModel> getStaticFilteredPropertyList() {
		return Arrays.asList(new StaticFilteringModel("I am 1.1", "I am 2.2", "I am 3.3"),
				new StaticFilteringModel("I am 1", "I am 2", "I am 3"));
	}
	
	/**
	 * Gets the dynamic filtered property.
	 *
	 * @return the dynamic filtered property
	 */
	@Operation(summary = "Gets the dynamically filtered property")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MappingJacksonValue.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = MappingJacksonValue.class)),
	}))
	@GetMapping(path = "/getDynamicFilteredProperty", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public MappingJacksonValue getDynamicFilteredProperty() {
		return getDynamicallyFilteredProperty(new DynamicFilteringModel("I am one", "I am two", "I am three"),
				"DynamicFilteringModel", "propOne", "propThree");
	}
	
	/**
	 * Gets the dynamic filtered property list.
	 *
	 * @return the dynamic filtered property list
	 */
	@Operation(summary = "Gets the dynamically filtered property list")
	@ApiResponses(@ApiResponse(content = {
			@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MappingJacksonValue.class)),
			@Content(mediaType = MediaType.APPLICATION_XML_VALUE, schema = @Schema(implementation = MappingJacksonValue.class)),
	}))
	@GetMapping(path = "/getDynamicFilteredPropertyList", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public MappingJacksonValue getDynamicFilteredPropertyList() {
		return getDynamicallyFilteredProperty(
				Arrays.asList(new DynamicFilteringModel("I am one", "I am two", "I am three"),
						new DynamicFilteringModel("I am one.1", "I am two.2", "I am three.3")),
				"DynamicFilteringModel", "propOne", "propThree");
	}

	/**
	 * Gets the dynamically filtered property.
	 *
	 * @param dynamicModel the dynamic model
	 * @param filteringModelName the filtering model name
	 * @param propertyArray the property array
	 * @return the dynamically filtered property
	 */
	private MappingJacksonValue getDynamicallyFilteredProperty(final Object dynamicModel,
			final String filteringModelName, final String... propertyArray) {
		final SimpleBeanPropertyFilter propFilter = SimpleBeanPropertyFilter.filterOutAllExcept(propertyArray);
		final FilterProvider filterProvider = new SimpleFilterProvider().addFilter(filteringModelName, propFilter);
		final MappingJacksonValue mappedVal = new MappingJacksonValue(dynamicModel);
		mappedVal.setFilters(filterProvider);
		return mappedVal;
	}
}
