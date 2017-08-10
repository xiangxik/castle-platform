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
package br.com.trustsystems.elfinder;

public final class ElFinderConstants {

    //global constants
    public static final int ELFINDER_TRUE_RESPONSE = 1;
    public static final int ELFINDER_FALSE_RESPONSE = 0;

    //options
    public static final String ELFINDER_VERSION_API = "2.1";

    //security
    // regex that matches any character that
    // occurs zero or more times (finds any character sequence)
    public static final String ELFINDER_VOLUME_SERCURITY_REGEX = "_.*";

    //api commands parameters
    public static final String ELFINDER_PARAMETER_INIT = "init";
    public static final String ELFINDER_PARAMETER_TREE = "tree";
    public static final String ELFINDER_PARAMETER_TARGET = "target";
    public static final String ELFINDER_PARAMETER_API = "api";
    public static final String ELFINDER_PARAMETER_NETDRIVERS = "netDrivers";
    public static final String ELFINDER_PARAMETER_FILES = "files";
    public static final String ELFINDER_PARAMETER_CWD = "cwd";
    public static final String ELFINDER_PARAMETER_OPTIONS = "options";
    public static final String ELFINDER_PARAMETER_HASH = "hash";
    public static final String ELFINDER_PARAMETER_MIME = "mime";
    public static final String ELFINDER_PARAMETER_TIMESTAMP = "ts";
    public static final String ELFINDER_PARAMETER_SIZE = "size";
    public static final String ELFINDER_PARAMETER_READ = "read";
    public static final String ELFINDER_PARAMETER_WRITE = "write";
    public static final String ELFINDER_PARAMETER_LOCKED = "locked";
    public static final String ELFINDER_PARAMETER_THUMBNAIL = "tmb";
    public static final String ELFINDER_PARAMETER_PARENTHASH = "phash";
    public static final String ELFINDER_PARAMETER_DIRECTORY_FILE_NAME = "name";
    public static final String ELFINDER_PARAMETER_VOLUME_ID = "volumeid";
    public static final String ELFINDER_PARAMETER_HAS_DIR = "dirs";
    public static final String ELFINDER_PARAMETER_PATH = "path";
    public static final String ELFINDER_PARAMETER_COMMAND_DISABLED = "disabled";
    public static final String ELFINDER_PARAMETER_FILE_SEPARATOR = "/";
    public static final String ELFINDER_PARAMETER_OVERWRITE_FILE = "copyOverwrite";
    public static final String ELFINDER_PARAMETER_ARCHIVERS = "archivers";
    public static final String ELFINDER_PARAMETER_COMMAND = "cmd";
    public static final String ELFINDER_PARAMETER_TARGETS = "targets[]";
    public static final String ELFINDER_PARAMETER_SEARCH_QUERY = "q";
    public static final String ELFINDER_PARAMETER_CONTENT = "content";
    public static final String ELFINDER_PARAMETER_LIST = "list";
    public static final String ELFINDER_PARAMETER_NAME = "name";
    public static final String ELFINDER_PARAMETER_FILE_DESTINATION = "dst";
    public static final String ELFINDER_PARAMETER_CUT = "cut";
    public static final String ELFINDER_PARAMETER_TYPE = "type";

    //api commands json header
    public static final String ELFINDER_JSON_RESPONSE_ADDED = "added";
    public static final String ELFINDER_JSON_RESPONSE_REMOVED = "removed";
    public static final String ELFINDER_JSON_RESPONSE_CHANGED = "changed";
    public static final String ELFINDER_JSON_RESPONSE_DIM = "dim";
    public static final String ELFINDER_JSON_RESPONSE_ERROR = "error";
    public static final String ELFINDER_JSON_RESPONSE_SIZE = "size";
}
