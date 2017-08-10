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

import br.com.trustsystems.elfinder.support.concurrency.GenericCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class CommandFactory implements ElfinderCommandFactory {

    private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

    private String classNamePattern;

    private final GenericCache<String, ElfinderCommand> cache = new GenericCache<>();

    @Override
    public ElfinderCommand get(final String commandName) {
        if (commandName == null || commandName.trim().isEmpty()) {
            logger.error(String.format("Command %s cannot be null or empty", commandName));
            throw new RuntimeException(String.format("Command %s cannot be null or empty", commandName));
        }

        ElfinderCommand command = null;

        try {
            command = cache.getValue(commandName, new Callable<ElfinderCommand>() {
                @Override
                public ElfinderCommand call() throws Exception {
                    logger.debug(String.format("trying recovery command!: %s", commandName));
                    String className = String.format(getClassNamePattern(), commandName.substring(0, 1).toUpperCase() + commandName.substring(1));
                    return (ElfinderCommand) Class.forName(className).newInstance();
                }
            });
            logger.debug(String.format("command found!: %s", commandName));
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Unable to get/create command instance.", e);
        }
        return command;
    }

    private String getClassNamePattern() {
        return classNamePattern;
    }

    public void setClassNamePattern(String classNamePattern) {
        this.classNamePattern = classNamePattern;
    }
}
