/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.aliyuncs.dysmsapi.model;

import java.util.List;
import com.aliyuncs.AcsResponse;
import com.aliyuncs.dysmsapi.transform.QueryInterSmsIsoInfoResponseUnmarshaller;
import com.aliyuncs.transform.UnmarshallerContext;

/**
 * @author auto create
 * @version 
 */
public class QueryInterSmsIsoInfoResponse extends AcsResponse {

	private String requestId;

	private String code;

	private String message;

	private String totalCount;

	private List<IsoSupportDTO> isoSupportDTOs;

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<IsoSupportDTO> getIsoSupportDTOs() {
		return this.isoSupportDTOs;
	}

	public void setIsoSupportDTOs(List<IsoSupportDTO> isoSupportDTOs) {
		this.isoSupportDTOs = isoSupportDTOs;
	}

	public static class IsoSupportDTO {

		private String countryName;

		private String countryCode;

		private String isoCode;

		public String getCountryName() {
			return this.countryName;
		}

		public void setCountryName(String countryName) {
			this.countryName = countryName;
		}

		public String getCountryCode() {
			return this.countryCode;
		}

		public void setCountryCode(String countryCode) {
			this.countryCode = countryCode;
		}

		public String getIsoCode() {
			return this.isoCode;
		}

		public void setIsoCode(String isoCode) {
			this.isoCode = isoCode;
		}
	}

	@Override
	public QueryInterSmsIsoInfoResponse getInstance(UnmarshallerContext context) {
		return	QueryInterSmsIsoInfoResponseUnmarshaller.unmarshall(this, context);
	}
}
