/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portletcontainer.descriptor.*;

import java.util.Properties;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

/**
 * This is the base fixture for service testing. Provides a service factory and the
 * properties file.
 */
public class DescriptorTest extends TestCase {


    public DescriptorTest(String name) {
        super(name);
    }

    public static void main (String[] args) throws Exception{
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite ( ) {
        return new TestSuite(DescriptorTest.class);
    }

    public void testDescriptor() {
        PortletDeploymentDescriptor pdd = null;
        String portletFile = System.getProperty("user.dir") + "/webapps/WEB-INF/conf/portlet-test.xml";
        String mappingFile = System.getProperty("user.dir") + "/webapps/WEB-INF/conf/portlet-mapping.xml";
        try {
            pdd = new PortletDeploymentDescriptor(portletFile, mappingFile);
        } catch (Exception e) {
            fail("Unable to unmarshall " + portletFile + " using " + mappingFile + " : " + e.getMessage());
        }
        List defs = pdd.getPortletDef();

        // we have one app descriptions
        assertEquals(defs.size(), 1);

        PortletDefinition def = (PortletDefinition)defs.get(0);
        PortletApplication portletApp = def.getPortletApp();
        List concreteApps = def.getConcreteApps();

        // we have two concrete portlet apps
        assertEquals(concreteApps.size(), 2);
        ConcretePortletApplication concreteOne = (ConcretePortletApplication)concreteApps.get(0);
        ConcretePortletApplication concreteTwo = (ConcretePortletApplication)concreteApps.get(1);

        assertEquals(portletApp.getUID(), "org.gridlab.gridsphere.portlets.core.HelloWorld.666");
        assertEquals(portletApp.getName(), "Hello World Portlet Application");

        PortletInfo portletInfo = portletApp.getPortletInfo();
        assertEquals(portletInfo.getHref(), "WEB-INF/web.xml#Servlet_1902648613");
        assertEquals(portletInfo.getId(), "Portlet_1");
        assertEquals(portletInfo.getName(), "Hello World");

        // Check concrete one portal data
        assertEquals(concreteOne.getName(), "Concrete Hello World - Portlet Sample #1");
        assertEquals(concreteOne.getUID(), "org.gridlab.gridsphere.portlets.core.HelloWorld.666.2");

        List configList = concreteOne.getConfigParamList();
        assertEquals(configList.size(), 2);
        ConfigParam one = (ConfigParam)configList.get(0);
        ConfigParam two = (ConfigParam)configList.get(1);

        assertEquals(one.getParamName(), "Portlet Master");
        assertEquals(one.getParamValue(), "master@domain.com");

        assertEquals(two.getParamName(), "Portlet Mistress");
        assertEquals(two.getParamValue(), "mistress@domain.com");

        ConcretePortletInfo onePI = concreteOne.getConcretePortletInfo();
        assertEquals(onePI.getHref(), "Portlet_1902648613");
        assertEquals(onePI.getName(), "Hello World");
        assertEquals(onePI.getDefaultLocale(), "en");

        List langList = onePI.getLanguageList();
        assertEquals(langList.size(), 2);
        LanguageInfo langOne = (LanguageInfo)langList.get(0);
        LanguageInfo langTwo = (LanguageInfo)langList.get(1);

        assertEquals(langOne.getDescription(), "Here is a simple portlet");
        assertEquals(langOne.getKeywords(), "portlet hello world");
        assertEquals(langOne.getLocale(), "en_US");
        assertEquals(langOne.getTitle(), "Hello World - Sample Portlet #1");
        assertEquals(langOne.getTitleShort(), "Hello World");

        assertEquals(langTwo.getDescription(), "Hier ist ein gleicht portlet");
        assertEquals(langTwo.getKeywords(), "portlet hallo welt");
        assertEquals(langTwo.getLocale(), "en_DE");
        assertEquals(langTwo.getTitle(), "Hallo Welt - Sample Portlet #1");
        assertEquals(langTwo.getTitleShort(), "Hallo Welt");

        List groups = onePI.getGroupList();
        assertEquals(groups.size(), 1);

        Group g = (Group)groups.get(0);
        assertEquals(g.getGroupName(), "ANY");

        List roles = onePI.getRoleList();
        assertEquals(groups.size(), 1);
        Role r = (Role)roles.get(0);
        assertEquals(r.getUserRole(), "GUEST");


    // Check concrete two portal data
        assertEquals(concreteTwo.getName(), "Concrete Hello World - Portlet Sample #2");
        assertEquals(concreteTwo.getUID(), "org.gridlab.gridsphere.portlets.core.HelloWorld.666.4");

        configList = concreteTwo.getConfigParamList();
        assertEquals(configList.size(), 1);
        one = (ConfigParam)configList.get(0);

        assertEquals(one.getParamName(), "Portlet Master");
        assertEquals(one.getParamValue(), "secondguy@some.com");

        onePI = concreteTwo.getConcretePortletInfo();
        assertEquals(onePI.getHref(), "Portlet_1902648615");
        assertEquals(onePI.getName(), "Hello World");
        assertEquals(onePI.getDefaultLocale(), "en");

        langList = onePI.getLanguageList();
        assertEquals(langList.size(), 1);
        langOne = (LanguageInfo)langList.get(0);

        assertEquals(langOne.getDescription(), "Here is another simple portlet");
        assertEquals(langOne.getKeywords(), "portlet hello world");
        assertEquals(langOne.getLocale(), "en_US");
        assertEquals(langOne.getTitle(), "Hello World - Sample Portlet #2");
        assertEquals(langOne.getTitleShort(), "Hello World");

        List groupsList = onePI.getGroupList();
        assertEquals(groups.size(), 1);

        Group gr = (Group)groups.get(0);
        assertEquals(gr.getGroupName(), "CACTUS");

        List rolez = onePI.getRoleList();
        assertEquals(groups.size(), 1);
        Role rol = (Role)roles.get(0);
        assertEquals(rol.getUserRole(), "USER");

    }


}
