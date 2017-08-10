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
package br.com.trustsystems.elfinder.configuration;

import br.com.trustsystems.elfinder.configuration.jaxb.ElfinderConfiguration;
import br.com.trustsystems.elfinder.exception.ElfinderConfigurationException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

/**
 * JAXB class to validate and read Elfinder Configuration XML File.
 *
 * @author Thiago Gutenberg Carvalho da Costa
 */
public class ElfinderConfigurationJaxb {

    public static final String SCHEMA_XML = "elfinder-configuration.xsd";

    private static final ElfinderConfigurationJaxb INSTANCE = new ElfinderConfigurationJaxb();

    private final Unmarshaller unmarshaller;

    private ElfinderConfigurationJaxb() throws ElfinderConfigurationException {
        try {
            // jaxb context
            JAXBContext context = JAXBContext.newInstance(br.com.trustsystems.elfinder.configuration.jaxb.ElfinderConfiguration.class.getPackage().getName());

            // creates unmarshaller
            this.unmarshaller = context.createUnmarshaller();

            // xml schema factory
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            InputStream xsdStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(SCHEMA_XML);
            Schema schema = schemaFactory.newSchema(new StreamSource(xsdStream));

            this.unmarshaller.setSchema(schema);

        } catch (JAXBException ex) {
            throw new ElfinderConfigurationException("Could not create the elFinder configuration JAXBContext instance", ex);
        } catch (SAXException ex) {
            throw new ElfinderConfigurationException("Could not load elFinder configuration schema xml " + SCHEMA_XML, ex);
        }
    }

    /**
     * Gets elFinder Configuration
     *
     * @return possible object is {@link ElfinderConfiguration }
     * @throws ElfinderConfigurationException
     */
    public static ElfinderConfiguration unmarshal(InputStream is) throws ElfinderConfigurationException {
        if (null != is) {
            try {
                return (ElfinderConfiguration) ElfinderConfigurationJaxb.INSTANCE.unmarshaller.unmarshal(is);
            } catch (JAXBException e) {
                throw new ElfinderConfigurationException("Could not unmarshal elFinder configuration xml", e);
            }
        }
        throw new ElfinderConfigurationException("Could not find elFinder configuration xml from " + ElfinderConfigurationWrapper.XML_PATH);
    }
}
