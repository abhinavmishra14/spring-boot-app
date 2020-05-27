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
package com.github.abhinavmishra14.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.abhinavmishra14.model.Response;

/**
 * The Class HelloController.
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    /**
     * Hello post.
     *
     * @param aStr the a str
     * @return the response
     */
    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Response helloPost(@RequestBody final String aStr){
    	LOGGER.info("Input data: {}", aStr);
        return new Response(aStr, "Welcome!");
    }
}
