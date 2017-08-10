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

import br.com.trustsystems.elfinder.core.Target;

import java.io.IOException;

/**
 * Archiver interface.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public interface Archiver {

    /**
     * Gets the archive name.
     *
     * @return the archive name
     */
    String getArchiveName();

    /**
     * Gets the archive mimetype.
     *
     * @return the archive mimetype
     */
    String getMimeType();

    /**
     * Gets the archive extension.
     *
     * @return the archive extension
     */
    String getExtension();

    /**
     * Creates the compress archive for the given targets.
     *
     * @param targets the targets to compress.
     * @return the target of the compress archive.
     * @throws IOException if something goes wrong.
     */
    Target compress(Target... targets) throws IOException;

    /**
     * Decompress the archive for the given target
     *
     * @param target the compress archive to decompress.
     * @return the target folder of the decompressed targets.
     * @throws IOException if something goes wrong.
     */
    Target decompress(Target target) throws IOException;
}
