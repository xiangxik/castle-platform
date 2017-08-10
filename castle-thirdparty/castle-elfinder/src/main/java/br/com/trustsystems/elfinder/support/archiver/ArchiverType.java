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
package br.com.trustsystems.elfinder.support.archiver;

/**
 * Archiver Supported Types.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public enum ArchiverType {

    TAR {
        @Override
        public Archiver getStrategy() {
            return new TarArchiver();
        }
    },
    ZIP {
        @Override
        public Archiver getStrategy() {
            return new ZipArchiver();
        }
    },
    GZIP {
        @Override
        public Archiver getStrategy() {
            return new GzipArchiver();
        }
    };

    // convenient

    public static ArchiverType of(String mimeType) {
        for (ArchiverType archiverType : values()) {
            if (archiverType.getStrategy().getMimeType().equalsIgnoreCase(mimeType)) {
                return archiverType;
            }
        }
        throw new RuntimeException(String.format("Archive type (%s) not supported", mimeType));
    }

    public static String[] SUPPORTED_MIME_TYPES = {
            ZIP.getMimeType(),
            TAR.getMimeType(),
            GZIP.getMimeType()
    };

    // shortcuts

    public String getMimeType() {
        return getStrategy().getMimeType();
    }

    public String getExtension() {
        return getStrategy().getExtension();
    }

    public String getArchiveName() {
        return getStrategy().getArchiveName();
    }

    public abstract Archiver getStrategy();

}
