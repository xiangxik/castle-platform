/*
 * #%L
 * %%
 * Copyright (C) 2015 Trustsystems Desenvolvimento de Sistemas, LTDA.
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Trustsystems Desenvolvimento de Sistemas, LTDA. nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package br.com.trustsystems.elfinder.web.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import br.com.trustsystems.elfinder.ElFinderConstants;
import br.com.trustsystems.elfinder.command.ElfinderCommand;
import br.com.trustsystems.elfinder.command.ElfinderCommandFactory;
import br.com.trustsystems.elfinder.core.ElfinderContext;
import br.com.trustsystems.elfinder.service.ElfinderStorageFactory;

@Controller
@RequestMapping("/elfinder/connector")
public class ConnectorController {
	private static final Logger logger = LoggerFactory.getLogger(ConnectorController.class);

	public static final String OPEN_STREAM = "openStream";
	public static final String GET_PARAMETER = "getParameter";

	@Resource(name = "commandFactory")
	private ElfinderCommandFactory elfinderCommandFactory;

	@Resource(name = "elfinderStorageFactory")
	private ElfinderStorageFactory elfinderStorageFactory;

	@Autowired
	private MultipartResolver multipartResolver;

	@RequestMapping
	public void connector(HttpServletRequest request, final HttpServletResponse response) throws IOException {
		try {
			request = processMultipartContent(request);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}

		String cmd = request.getParameter(ElFinderConstants.ELFINDER_PARAMETER_COMMAND);
		ElfinderCommand elfinderCommand = elfinderCommandFactory.get(cmd);

		try {
			final HttpServletRequest protectedRequest = request;
			elfinderCommand.execute(new ElfinderContext() {
				@Override
				public ElfinderStorageFactory getVolumeSourceFactory() {
					return elfinderStorageFactory;
				}

				@Override
				public HttpServletRequest getRequest() {
					return protectedRequest;
				}

				@Override
				public HttpServletResponse getResponse() {
					return response;
				}
			});
		} catch (Exception e) {
			logger.error("Unknown error", e);
		}
	}

	private HttpServletRequest processMultipartContent(final HttpServletRequest request) throws Exception {
		if (!multipartResolver.isMultipart(request)) {
			return request;
		}

		MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);
		multipartRequest.setAttribute(MultipartFile.class.getName(), multipartRequest.getFileMap().values());
		return multipartRequest;
	}
}