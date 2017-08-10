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
package br.com.trustsystems.elfinder.service;

import br.com.trustsystems.elfinder.configuration.ElfinderConfigurationUtils;
import br.com.trustsystems.elfinder.core.Volume;
import br.com.trustsystems.elfinder.core.VolumeBuilder;
import br.com.trustsystems.elfinder.core.impl.NIO2FileSystemVolume;
import br.com.trustsystems.elfinder.exception.VolumeSourceException;

import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Arrays;

/**
 * Volume Sources supported.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public enum VolumeSources {

    FILESYSTEM {
        @Override
        public VolumeBuilder<?> getVolumeBuilder(String alias, String path) {
            return NIO2FileSystemVolume.builder(alias, Paths.get(ElfinderConfigurationUtils.toURI(path)));
        }
    }
//    ,DROPBOX, GOOGLEDRIVE, ONEDRIVE, ICLOUD
    ;

    public static VolumeSources of(String source) {
        if (source != null) {

            final String notLetterRegex = "[^\\p{L}]";
            final String whitespaceRegex = "[\\p{Z}]";
            final String notAsciiCharactersRegex = "[^\\p{ASCII}]";
            final String emptyString = "";

            source = Normalizer.normalize(source, Normalizer.Form.NFD);
            source = source.replaceAll(notLetterRegex, emptyString);
            source = source.replaceAll(whitespaceRegex, emptyString);
            source = source.replaceAll(notAsciiCharactersRegex, emptyString);
            source = source.trim().toUpperCase();

            for (VolumeSources volumesource : values()) {
                if (volumesource.name().equalsIgnoreCase(source)) {
                    return volumesource;
                }
                throw new VolumeSourceException("Volume source not supported! The supported volumes sources are: " + Arrays.deepToString(values()).toLowerCase());
            }
        }
        throw new VolumeSourceException("Volume source not informed in elfinder configuration xml!");
    }

    public Volume newInstance(String alias, String path) {
        if (path == null || path.trim().isEmpty())
            throw new VolumeSourceException("Volume source path not informed");
        return getVolumeBuilder(alias, path).build();
    }

    public abstract VolumeBuilder<?> getVolumeBuilder(String alias, String path);
}
