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
package com.github.abhinavmishra14.currexc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.abhinavmishra14.currexc.model.ExchangeRatesModel;

/**
 * The Interface ExchangeValueRepository.
 */
public interface ExchangeRatesRepository extends JpaRepository<ExchangeRatesModel, Long> {
	
	/**
	 * Find by from and to.
	 *
	 * @param from the from
	 * @param to the to
	 * @return the exchange rates model
	 */
	ExchangeRatesModel findByFromAndTo(final String from, final String to);
}
