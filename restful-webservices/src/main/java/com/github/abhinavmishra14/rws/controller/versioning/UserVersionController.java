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
package com.github.abhinavmishra14.rws.controller.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class UserVersionController.
 */
@RestController
public class UserVersionController {
	
	/**
	 * Gets the user V 1.
	 *
	 * @return the user V 1
	 */
	@GetMapping("/v1/user")
	public UserV1 getUserV1() {
		return new UserV1("Abhinav Mishra");
	}

	/**
	 * Gets the user V 2.
	 *
	 * @return the user V 2
	 */
	@GetMapping("/v2/user")
	public UserV2 getUserV2() {
		return new UserV2(new Name("Abhinav", "Mishra"));
	}

	/**
	 * Gets the user V 1 using param.
	 *
	 * @return the user V 1 using param
	 */
	@GetMapping(value = "/user/param", params = "version=1")
	public UserV1 getUserV1UsingParam() {
		return new UserV1("Abhinav Mishra");
	}

	/**
	 * Gets the user V 2 using param.
	 *
	 * @return the user V 2 using param
	 */
	@GetMapping(value = "/user/param", params = "version=2")
	public UserV2 getUserV2UsingParam() {
		return new UserV2(new Name("Abhinav", "Mishra"));
	}

	/**
	 * Gets the user V 1 using header.
	 *
	 * @return the user V 1 using header
	 */
	@GetMapping(value = "/user/header", headers = "X-API-VERSION=1")
	public UserV1 getUserV1UsingHeader() {
		return new UserV1("Abhinav Mishra");
	}

	/**
	 * Gets the user V2 using header.
	 *
	 * @return the user V2 using header
	 */
	@GetMapping(value = "/user/header", headers = "X-API-VERSION=2")
	public UserV2 getUserV2UsingHeader() {
		return new UserV2(new Name("Abhinav", "Mishra"));
	}

	/**
	 * Gets the user V1 using media type.
	 *
	 * @return the user V1 using media type
	 */
	@GetMapping(value = "/user/produces", produces = "application/vnd.company.app-v1+json")
	public UserV1 getUserV1UsingMediaType() {
		return new UserV1("Abhinav Mishra");
	}

	/**
	 * Gets the user V2 using media type.
	 *
	 * @return the user V2 using media type
	 */
	@GetMapping(value = "/user/produces", produces = "application/vnd.company.app-v2+json")
	public UserV2 getUserV2UsingMediaType() {
		return new UserV2(new Name("Abhinav", "Mishra"));
	}
}