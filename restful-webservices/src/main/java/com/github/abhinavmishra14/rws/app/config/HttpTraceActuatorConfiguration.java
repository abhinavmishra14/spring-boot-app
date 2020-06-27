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

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class HttpTraceActuatorConfiguration.
 */
@Configuration
public class HttpTraceActuatorConfiguration {
	
	/**
	 * Http trace repository.
	 *
	 * @return the http trace repository
	 */
	@Bean
	public HttpTraceRepository httpTraceRepository() {
		//You can provide your custom implementation here or use InMemoryHttpTraceRepository.
		//In custom implementation you can use any nosql db such as mongodb to store all traces instead of doing in memory.
		//In memory traces go away when you restart the server.
		return new InMemoryHttpTraceRepository();
	}
}
