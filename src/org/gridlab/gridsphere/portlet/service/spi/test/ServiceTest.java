/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.service.spi.test;

import junit.framework.TestCase;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

import java.util.Properties;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

import com.mockobjects.servlet.MockServletConfig;
import com.mockobjects.servlet.MockServletContext;

/**
 * This is the base fixture for service testing. Provides a service factory and the
 * properties file.
 */
public class ServiceTest extends TestCase {

    protected static SportletServiceFactory factory = null;
    protected static Properties props = null;
    private static PortletLog log = SportletLog.getInstance(ServiceTest.class);


    public ServiceTest(String name) {
        super(name);
    }

    protected void setUp() {

        // create factory
        factory = SportletServiceFactory.getInstance();

        // create a few Mock objects
        //MockServletConfig mockConfig = new MockServletConfig();
        //MockServletContext mockContext = new MockServletContext();
        //mockContext.addRealPath("");

        // create properties
        props = new Properties();
        FileInputStream fistream = null;
        String fullPath = System.getProperty("user.dir") + "/webapps/WEB-INF/conf/PortletServices.properties";
        log.info("loading properties file: " + fullPath);
        try {
            fistream = new FileInputStream(fullPath);
            props.load(fistream);
        } catch (IOException e) {
            log.error("Unable to load properties file", e);
        }

    }

    protected void tearDown() {
        SportletServiceFactory.getInstance().shutdownServices();
    }
}
