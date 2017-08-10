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
package br.com.trustsystems.elfinder.command;

import br.com.trustsystems.elfinder.service.ElfinderStorage;
import br.com.trustsystems.elfinder.service.VolumeHandler;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class FileCommand extends AbstractCommand implements ElfinderCommand {

    public static final String STREAM = "1";

    @Override
    public void execute(ElfinderStorage elfinderStorage, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String target = request.getParameter("target");
        boolean download = STREAM.equals(request.getParameter("download"));
        VolumeHandler fsi = super.findTarget(elfinderStorage, target);
        String mime = fsi.getMimeType();

        response.setCharacterEncoding("utf-8");
        response.setContentType(mime);
        String fileName = fsi.getName();
        if (download) {
            response.setHeader("Content-Disposition",
                    "attachments; " + getAttachementFileName(fileName, request.getHeader("USER-AGENT")));
            response.setHeader("Content-Transfer-Encoding", "binary");
        }

        OutputStream out = response.getOutputStream();
        response.setContentLength((int) fsi.getSize());

        try (InputStream is = fsi.openInputStream()) {
            IOUtils.copy(is, out);
            out.flush();
            out.close();
        }
    }

    private String getAttachementFileName(String fileName, String userAgent) throws UnsupportedEncodingException {
        if (userAgent != null) {
            userAgent = userAgent.toLowerCase();

            if (userAgent.contains("msie")) {
                return "filename=\"" + URLEncoder.encode(fileName, "UTF8") + "\"";
            }

            if (userAgent.contains("opera")) {
                return "filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF8");
            }
            if (userAgent.contains("safari")) {
                return "filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\"";
            }
            if (userAgent.contains("mozilla")) {
                return "filename*=UTF-8''" + URLEncoder.encode(fileName, "UTF8");
            }
        }

        return "filename=\"" + URLEncoder.encode(fileName, "UTF8") + "\"";
    }
}
