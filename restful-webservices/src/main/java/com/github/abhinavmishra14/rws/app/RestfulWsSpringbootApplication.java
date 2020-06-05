/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2017. Abhinav Kumar Mishra. 
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
package com.github.abhinavmishra14.rws.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * The Class RestfulWsSpringbootApplication.<br><br>
 * 
 * If Controller/Model/Service classes are not in the same package, then @ComponentScan annotation needs to be added <br>
 * e.g. RestfulWsSpringbootApplication is in package com.github.abhinavmishra14.rws.app where other classes are in different package 
 * such as: com.github.abhinavmishra14.rws.controller, com.github.abhinavmishra14.rws.model <br><br>
 * 
 * If Controller/Model/Service classes are in same package then @ComponentScan not needed. <br>
 * e.g. RestfulWsSpringbootApplication is in package com.github.abhinavmishra14.rws.app and other classes are also in same package 
 * such as: com.github.abhinavmishra14.rws.ap.controller, com.github.abhinavmishra14.rws.ap.model <br><br>
 */
@SpringBootApplication
@ComponentScan({"com.github.abhinavmishra14.rws.controller", "com.github.abhinavmishra14.rws.model"})
public class RestfulWsSpringbootApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		SpringApplication.run(RestfulWsSpringbootApplication.class, args);
	}
}
