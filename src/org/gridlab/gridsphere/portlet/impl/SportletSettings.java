/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.Client;
import org.gridlab.gridsphere.portlet.PortletApplicationSettings;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.portletcontainer.ConcretePortlet;
import org.gridlab.gridsphere.portletcontainer.ConcretePortletConfig;
import org.gridlab.gridsphere.portletcontainer.impl.descriptor.LanguageInfo;

import java.io.IOException;
import java.util.*;

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
    protected String concretePortletID = new String();
    protected SportletApplicationSettings appSettings = null;
    protected Locale locale = null;

    /**
     * Disallow default instantiation
     */
    private SportletSettings() {
    }

    /**
     * SportletSettings constructor
     * Create a PortletSettings object from a concrete portlet
     *
     * @param concPortlet the concrete portlet
     */
    public SportletSettings(ConcretePortlet concPortlet) {

        this.concPortlet = concPortlet;
        this.concretePortletID = concPortlet.getConcretePortletID();
        this.appSettings = new SportletApplicationSettings(concPortlet);

        ConcretePortletConfig concPortletConf =
                concPortlet.getConcretePortletConfig();
        String localeStr = concPortletConf.getDefaultLocale();
        locale = new Locale(localeStr, "");
        langList = concPortletConf.getLanguageList();

        // Stick <config-param> in store
        store = concPortletConf.getConfigAttributes();
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
     * @return the title of the portlet or null if none exists for the provided locale and client
     */
    public String getTitle(Locale locale, Client client) {
        String defaultTitle = null;
        Iterator it = langList.iterator();
        while (it.hasNext()) {
            LanguageInfo langInfo = (LanguageInfo) it.next();
            if (langInfo.getLocale().startsWith(locale.toString())) {
                return langInfo.getTitle();
            }
        }
        return defaultTitle;
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
     * Returns the short title of this window for the provided locale, or whatever exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getTitleShort(Locale locale, Client client) {
        String defaultTitleShort = "";
        Iterator it = langList.iterator();
        while (it.hasNext()) {
            LanguageInfo langInfo = (LanguageInfo) it.next();
            defaultTitleShort = langInfo.getTitleShort();
            if (langInfo.getLocale().equals(locale)) {
                return langInfo.getTitleShort();
            }
        }
        return defaultTitleShort;
    }

    /**
     * Returns the description of this window for the provided locale, or whatever exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getDescription(Locale locale, Client client) {
        String defaultDescription = "";
        Iterator it = langList.iterator();
        while (it.hasNext()) {
            LanguageInfo langInfo = (LanguageInfo)it.next();
            defaultDescription = langInfo.getDescription();
            if (langInfo.getLocale().equals(locale)) {
                return langInfo.getDescription();
            }
        }
        return defaultDescription;
    }

    /**
     * Returns the keywords of this window for the provided locale, or whatever exists.
     *
     * @param locale the locale-centric title
     * @param client the given client
     * @return the title of the portlet
     */
    public String getKeywords(Locale locale, Client client) {
        String defaultKeywords = "";
        Iterator it = langList.iterator();
        while (it.hasNext()) {
            LanguageInfo langInfo = (LanguageInfo) it.next();
            defaultKeywords = langInfo.getKeywords();
            if (langInfo.getLocale().equals(locale)) {
                return langInfo.getKeywords();
            }
        }
        return defaultKeywords;
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
     */
    public void removeAttribute(String name) {
        store.remove(name);
    }

    /**
     * Sets the attribute with the given name and value.
     *
     * @param name the attribute name
     * @param value the attribute value
     */
    public void setAttribute(String name, String value) {
        store.put(name, value);
    }

    /**
     * Stores all attributes.
     *
     * @throws IOException if the streaming causes an I/O problem
     */
    public void store() throws IOException {
        ConcretePortletConfig concPortletConf = concPortlet.getConcretePortletConfig();
        concPortletConf.setConfigAttributes(store);
        concPortlet.setConcretePortletConfig(concPortletConf);
        concPortlet.save();
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
