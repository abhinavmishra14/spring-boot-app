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
package com.github.abhinavmishra14.netflixzull.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * The Class ZuulLoggingFilter.
 */
@Component
public class ZuulLoggingFilter extends ZuulFilter {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ZuulLoggingFilter.class);

	/**
	 * Should filter.<br>
	 * Retrun true to execute this filter for all requests.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * Run.
	 *
	 * @return the object
	 * @throws ZuulException the zuul exception
	 */
	@Override
	public Object run() throws ZuulException {
		final HttpServletRequest request = 
				RequestContext.getCurrentContext().getRequest();
		LOGGER.info("Request -> {} request uri -> {}",  request, request.getRequestURI());
		return null;
	}

	/**
	 * Filter type.<br>
	 * Should the filtering be happening before (pre) the request, after (post) the request or only error (error) requests.
	 *
	 * @return the string
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * Filter order.<br>
	 * Set the priority order. e.g. 0,1,2....
	 *
	 * @return the int
	 */
	@Override
	public int filterOrder() {
		return 1;
	}
}
