/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.core.persistence.castor.descriptor.ConfigParam;
import org.gridlab.gridsphere.portletcontainer.descriptor.PortletDeploymentDescriptor;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletInfo;
import org.gridlab.gridsphere.portletcontainer.descriptor.ConcretePortletDescriptor;
import org.gridlab.gridsphere.portletcontainer.descriptor.LanguageInfo;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;

import java.util.*;
import java.io.IOException;

/**
 * The SportletSettings class provides the portlet with its dynamic configuration.
 * The configuration holds information about the portlet that is valid per concrete portlet for all users,
 * and is maintained by the administrator. The portlet can therefore only read the dynamic configuration.
 * Only when the portlet is in CONFIGURE mode, it has write access to the dynamic configuration data
 */
public class SportletSettings implements PortletSettings {

    protected ConcretePortlet concPortlet = null;
    protected Hashtable store = new Hashtable();
    protected List langList = new Vector();
    protected String concretePortletID = null;
    protected SportletApplicationSettings appSettings = null;
    protected Locale locale = null;

    /**
     * SportletSettings constructor
     * Create a PortletSettings object from a concrete portlet
     *
     * @param concPortlet the concrete portlet
     */
    public SportletSettings(ConcretePortlet concPortlet) {

        this.concPortlet = concPortlet;
        this.concretePortletID = concPortlet.getConcretePortletAppID();
        this.appSettings = new SportletApplicationSettings(concPortlet);

        ConcretePortletInfo concretePortletInfo =
                concPortlet.getConcretePortletDescriptor().getConcretePortletInfo();
        String localeStr = concretePortletInfo.getDefaultLocale();
        locale = new Locale(localeStr, "");
        langList = concretePortletInfo.getLanguageList();

        // Stick <config-param> in store
        store = concPortlet.getPortletContext();
        Iterator configParamsIt = concretePortletInfo.getConfigParamList().iterator();
        while (configParamsIt.hasNext()) {
            ConfigParam configParam = (ConfigParam)configParamsIt.next();
            store.put(configParam.getParamName(), configParam.getParamValue());
        }
    }

    /**
     * Returns the value of the attribute with the given name, or null if no such attribute exists.
     *
     * @param name the name of the attribute
     * @return the value of the attribute
     */
    public String getAttribute(String name) {
        return (String) store.get(name);
    }

    /**
     * Returns an enumeration of all available attributes names.
     *
     * @return an enumeration of all available attributes names
     */
    public Enumeration getAttributeNames() {
        return store.keys();
    }

    /**
     * Returns the title of this window for the provided locale, or null if none exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitle(Locale locale, Client client) {
        Iterator it = langList.iterator();
        while (it.hasNext()) {
            LanguageInfo langInfo = (LanguageInfo)it.next();
            if (langInfo.getLocale().startsWith(locale.toString())) {
                return langInfo.getTitle();
            }
        }
        return null;
    }

    /**
     * Returns the portlet's default locale.
     *
     * @return the portlet's default locale
     */
    public Locale getDefaultLocale() {
        return locale;
    }

    /**
     * Returns the short title of this window for the provided locale, or null if none exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitleShort(Locale locale, Client client) {
        while (langList.iterator().hasNext()) {
            LanguageInfo langInfo = (LanguageInfo)langList.iterator().next();
            if (langInfo.getLocale().equals(locale)) {
                return langInfo.getTitleShort();
            }
        }
        return null;
    }

    /**
     * Returns the description of this window for the provided locale, or null if none exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getDescription(Locale locale, Client client) {
        while (langList.iterator().hasNext()) {
            LanguageInfo langInfo = (LanguageInfo)langList.iterator().next();
            if (langInfo.getLocale().equals(locale)) {
                return langInfo.getDescription();
            }
        }
        return null;
    }

    /**
     * Returns the keywords of this window for the provided locale, or null if none exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getKeywords(Locale locale, Client client) {
        while (langList.iterator().hasNext()) {
            LanguageInfo langInfo = (LanguageInfo)langList.iterator().next();
            if (langInfo.getLocale().equals(locale)) {
                return langInfo.getKeywords();
            }
        }
        return null;
    }

    /**
     * Returns this portlets concrete ID. Used internally in Action tags
     * to signal the portlet container which portlet needs to be executed
     * NOTE: THIS IS NOT PART OF THE WPS PORTLET API 4.1
     *
     * @return the concrete portlet ID
     */
    public String getConcretePortletID() {
        return concretePortletID;
    }

    /**
     * Removes the attribute with the given name.
     *
     * @param name the attribute name
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void removeAttribute(String name) throws AccessDeniedException {
        store.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     */
    public void setAttribute(String name, String value) throws AccessDeniedException {
        store.put(name, value);
    }

    /**
     * Stores all attributes.
     *
     * @throws AccessDeniedException if the caller isn't authorized to access this data object
     * @throws IOException if the streaming causes an I/O problem
     */
    public void store() throws AccessDeniedException, IOException {
        ConcretePortletDescriptor concDescriptor = concPortlet.getConcretePortletDescriptor();
        ConcretePortletInfo concPortletInfo = concDescriptor.getConcretePortletInfo();
        Enumeration enum = store.elements();
        ArrayList list = new ArrayList();
        while (enum.hasMoreElements()) {
            String key = (String)enum.nextElement();
            String value = (String)store.get(key);
            ConfigParam parms = new ConfigParam(key, value);
            list.add(parms);
        }
        concPortletInfo.setConfigParamList(list);
        concDescriptor.setConcretePortletInfo(concPortletInfo);
        concPortlet.saveDescriptor(concDescriptor);
    }

    /**
     * Returns the portlet application settings
     *
     * @return the portlet application settings
     */
    public PortletApplicationSettings getApplicationSettings() {
        return appSettings;
    }

}
