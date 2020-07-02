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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.abhinavmishra14.rws.service.UserProfile;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//@Autowired
	//private Environment env;
	
	/** The user service. */
	@Autowired
    private UserProfile userService;

    /* (non-Javadoc)
     * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder)
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
	
	/* (non-Javadoc)
	 * @see org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter#configure(org.springframework.security.config.annotation.web.builders.HttpSecurity)
	 */
	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		httpSecurity.headers().frameOptions().sameOrigin(); //Allow h2-console
		httpSecurity.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	/**
	 * Configure global.
	 *
	 * @param authentication the authentication
	 * @throws Exception the exception
	 */
	/*
	 * @Autowired public void configureGlobal(final AuthenticationManagerBuilder
	 * authentication) throws Exception {
	 * authentication.inMemoryAuthentication().withUser(env.getProperty(
	 * "spring.security.user.name"))
	 * .password(passwordEncoder().encode(env.getProperty(
	 * "spring.security.user.password"))).authorities("ROLE_USER"); }
	 */

	/**
	 * Password encoder.
	 *
	 * @return the password encoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
