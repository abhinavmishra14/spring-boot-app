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
package com.github.abhinavmishra14.rws.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

/**
 * The Class RWSUtils.
 */
public final class RWSUtils {
	
	/**
	 * The Constructor.
	 */
	private RWSUtils() {
		super();
	}
	
	/**
	 * Builds the text message.
	 *
	 * @param throwable the throwable
	 * @param customMsgMap the custom msg map
	 * @return the string
	 */
	public static String buildTextMessage(final Throwable throwable,
			final Map<String,String> customMsgMap) {
		final StringBuffer result = new StringBuffer(100);
		result.append("<br/>-------------------------------------------------------------");
		if(customMsgMap!=null && !customMsgMap.isEmpty()){
			for (final Iterator<Entry<String, String>> iterator = customMsgMap
					.entrySet().iterator(); iterator.hasNext();) {
				final Entry<String, String> eachEntry = iterator.next();
				result.append("<br/><br/>").append(eachEntry.getKey()).append(": ").append(eachEntry.getValue());
			}
		}
		prepareExceptionDetails(throwable, result);
		return result.toString();
	}

	/**
	 * Builds the text message.
	 *
	 * @param throwable the throwable
	 * @param customMessage the custom message
	 * @return the string
	 */
	public static String buildTextMessage(final Throwable throwable,
			final String customMessage) {
		final StringBuffer result = new StringBuffer(100);
		result.append("<br/>-------------------------------------------------------------");
		if(StringUtils.isNotBlank(customMessage)){
			result.append("<br/><br/>Error: ").append(customMessage);
		}
		prepareExceptionDetails(throwable, result);
		return result.toString();
	}

	/**
	 * Prepare exception details.
	 *
	 * @param throwable the throwable
	 * @param result the result
	 */
	private static void prepareExceptionDetails(final Throwable throwable,
			final StringBuffer result) {
		final String timeOfFailure = new Date().toString();
		String hostName = StringUtils.EMPTY;
		String ipAddress = StringUtils.EMPTY;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			ipAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException uhe) {
			hostName = "unknown";
			ipAddress = "unknown";
		}
		result.append("<br/><br/>Time of failure: ").append(timeOfFailure)
				.append("<br/><br/>Host where failure occurred: ").append(hostName).append(" (").append(ipAddress)
				.append(")");

		if (throwable == null) {
			result.append("<br/><br/>No exception was provided.");
		} else {
			result.append("<br/><br/>Root exception:").append(renderExceptionStackAsText(throwable));
		}
	}

	/**
	 * Render exception stack as text.
	 *
	 * @param throwable the throwable
	 * @return the string
	 */
	private static String renderExceptionStackAsText(final Throwable throwable) {
		final StringBuffer result = new StringBuffer();
		if (throwable != null) {
			String message = throwable.getMessage();
			final Throwable cause = throwable.getCause();
			if (cause != null) {
				result.append(renderExceptionStackAsText(cause));
				result.append("<br/><br/>Wrapped by:");
			}
			
			if (message == null) {
				message = StringUtils.EMPTY;
			}
			
			result.append('\n');
			result.append(throwable.getClass().getName());
			result.append(": ");
			result.append(message);
			result.append('\n');
			result.append(renderStackTraceElements(throwable.getStackTrace()));
		}
		return result.toString();
	}

	/**
	 * Render stack trace elements.
	 *
	 * @param elements the elements
	 * @return the string
	 */
	public static String renderStackTraceElements(final StackTraceElement[] elements) {
		final StringBuffer result = new StringBuffer();
		if (elements != null) {
			for (int each = 0; each < elements.length; each++) {
				result.append("\tat " + elements[each].toString() + "\n");
			}
		}
		return result.toString();
	}
	
	/**
	 * Generate id.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the int
	 */
	public static int generateId(final int min, final int max) {
	    final Random random = new Random();
	    return random.nextInt((max - min) + 1) + min;
	}
}
