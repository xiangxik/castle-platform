
package br.com.trustsystems.elfinder.configuration.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.com.trustsystems.elfinder.configuration.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.com.trustsystems.elfinder.configuration.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ElfinderConfiguration }
     * 
     */
    public ElfinderConfiguration createElfinderConfiguration() {
        return new ElfinderConfiguration();
    }

    /**
     * Create an instance of {@link ElfinderConfiguration.Volume }
     * 
     */
    public ElfinderConfiguration.Volume createElfinderConfigurationVolume() {
        return new ElfinderConfiguration.Volume();
    }

    /**
     * Create an instance of {@link ElfinderConfiguration.Thumbnail }
     * 
     */
    public ElfinderConfiguration.Thumbnail createElfinderConfigurationThumbnail() {
        return new ElfinderConfiguration.Thumbnail();
    }

    /**
     * Create an instance of {@link ElfinderConfiguration.Volume.Constraint }
     * 
     */
    public ElfinderConfiguration.Volume.Constraint createElfinderConfigurationVolumeConstraint() {
        return new ElfinderConfiguration.Volume.Constraint();
    }

}
