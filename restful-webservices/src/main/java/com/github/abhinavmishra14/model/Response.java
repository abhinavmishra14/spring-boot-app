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
package com.github.abhinavmishra14.model;

/**
 * The Class Response.
 */
public class Response {
    
    /** The speech. */
    private final String speech;
    
    /** The display text. */
    private final String displayText;

    /** The source. */
    private final String source;

    /**
     * Instantiates a new response.
     *
     * @param speech the speech
     * @param displayText the display text
     */
	public Response(final String speech, final String displayText) {
        this.speech = speech;
        this.displayText = displayText;
        this.source = "sprngbootapp";
    }

    /**
     * Gets the speech.
     *
     * @return the speech
     */
    public String getSpeech() {
        return speech;
    }

    /**
     * Gets the display text.
     *
     * @return the display text
     */
    public String getDisplayText() {
        return displayText;
    }

    /**
     * Gets the source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }
}
