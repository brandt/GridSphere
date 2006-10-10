package org.gridsphere.portlet.jsrimpl;

import org.gridsphere.portletcontainer.impl.descriptor.InitParam;
import org.gridsphere.portletcontainer.impl.descriptor.PortletDefinition;
import org.gridsphere.portletcontainer.impl.descriptor.PortletInfo;
import org.gridsphere.portletcontainer.impl.descriptor.SupportedLocale;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.servlet.ServletConfig;
import java.util.*;

/**
 * The <CODE>PortletConfig</CODE> interface provides the portlet with
 * its configuration. The configuration holds information about the
 * portlet that is valid for all users. The configuration is retrieved
 * from the portlet definition in the deployment descriptor.
 * The portlet can only read the configuration data.
 * <p/>
 * The configuration information contains the portlet name, the portlet
 * initialization parameters, the portlet resource bundle and the portlet
 * application context.
 *
 * @see Portlet
 */
public class PortletConfigImpl implements PortletConfig {

    private PortletContext context = null;
    private ClassLoader classLoader = null;
    private String portletName = null;
    private ResourceBundle infoBundle = null;
    private String resources = null;
    private Hashtable initParams = new Hashtable();

    private static class DefaultResourceBundle extends ListResourceBundle {
        private Object[][] resources;

        public DefaultResourceBundle(PortletInfo portletInfo) {
            String title = ((portletInfo.getTitle() != null) ? portletInfo.getTitle().getContent() : "");
            String shortTitle = ((portletInfo.getShortTitle() != null) ? portletInfo.getShortTitle().getContent() : "");
            String keywords = ((portletInfo.getKeywords() != null) ? portletInfo.getKeywords().getContent() : "");
            resources = new Object[][]{
                {"javax.portlet.title", title},
                {"javax.portlet.short-title", shortTitle},
                {"javax.portlet.keywords", keywords}
            };
        }

        public Object[][] getContents() {
            return resources;
        }
    }

    private static class ResourceBundleImpl extends ResourceBundle {
        private HashMap data;

        public ResourceBundleImpl(ResourceBundle bundle, ResourceBundle defaults) {
            data = new HashMap();
            importData(defaults);
            importData(bundle);
        }

        private void importData(ResourceBundle bundle) {
            if (bundle != null) {
                for (Enumeration e = bundle.getKeys(); e.hasMoreElements();) {
                    String key = (String) e.nextElement();
                    Object value = bundle.getObject(key);
                    data.put(key, value);
                }
            }
        }

        protected Object handleGetObject(String key) {
            return data.get(key);
        }

        public Enumeration getKeys() {
            return new Enumerator(data.keySet());
        }
    }

    /**
     * Constructs an instance of PortletConfig from a servlet configuration
     * object and an application portlet descriptor
     *
     * @param servletConfig a <code>ServletConfig</code>
     * @param definition    a <code>PortletDefinition</code>
     */
    public PortletConfigImpl(ServletConfig servletConfig, PortletDefinition definition, ClassLoader classLoader) {

        this.classLoader = classLoader;

        // create init params
        InitParam[] params = definition.getInitParam();
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                InitParam iparam = params[i];
                String name = iparam.getName().getContent();
                String value = iparam.getValue().getContent();
                if ((name != null) && (value != null)) {
                    initParams.put(name, value);
                }
            }
        }

        // create portlet context
        context = new PortletContextImpl(servletConfig.getServletContext());

        // get portlet name
        portletName = definition.getPortletName().getContent();

        SupportedLocale[] locales = definition.getSupportedLocale();
        Locale[] supportedLocales = new Locale[locales.length];
        for (int i = 0; i < locales.length; i++) {
            supportedLocales[i] = new Locale(locales[i].getContent());
        }

        PortletInfo portletInfo = definition.getPortletInfo();
        if (portletInfo != null) {
            infoBundle = new DefaultResourceBundle(portletInfo);

        }

        if (definition.getResourceBundle() != null) {
            resources = definition.getResourceBundle().getContent();

        }

        //this.logConfig();
    }

    /**
     * Returns the name of the portlet.
     * <P>
     * The name may be provided via server administration, assigned in the
     * portlet application deployment descriptor with the <code>portlet-name</code>
     * tag.
     *
     * @return the portlet name
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Returns the <code>PortletContext</code> of the portlet application
     * the portlet is in.
     *
     * @return a <code>PortletContext</code> object, used by the
     *         caller to interact with its portlet container
     * @see PortletContext
     */
    public PortletContext getPortletContext() {
        return context;
    }

    /**
     * Returns the path name of this portlet context
     *
     * @return Returns the context path of the web application.
     */
    public String getContextPath() {
        // todo fix me to confirm to servlet spec 2.5
        return "";
    }

    /**
     * Gets the resource bundle for the given locale based on the
     * resource bundle defined in the deployment descriptor
     * with <code>resource-bundle</code> tag or the inlined resources
     * defined in the deployment descriptor.
     *
     * @param locale the locale for which to retrieve the resource bundle
     * @return the resource bundle for the given locale
     */
    public ResourceBundle getResourceBundle(java.util.Locale locale) {
        if (resources == null) {
            return infoBundle;
        }
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundle.getBundle(resources, locale, classLoader);
            if (infoBundle != null) {
                return new ResourceBundleImpl(resourceBundle, infoBundle);
            }
        } catch (MissingResourceException e) {
            System.err.println("Unable to find resource bundle: " + resources + " for locale: " + locale);
            if (infoBundle != null) {
                return infoBundle;
            }
        }
        return resourceBundle;
    }

    /**
     * Returns a String containing the value of the named initialization parameter,
     * or null if the parameter does not exist.
     *
     * @param name a <code>String</code> specifying the name
     *             of the initialization parameter
     * @return		a <code>String</code> containing the value
     * of the initialization parameter
     * @exception	IllegalArgumentException if name is <code>null</code>.
     */
    public String getInitParameter(String name) {
        if (name == null) throw new IllegalArgumentException("name is NULL");
        return (String) initParams.get(name);
    }


    /**
     * Returns the names of the portlet initialization parameters as an
     * <code>Enumeration</code> of String objects, or an empty <code>Enumeration</code> if the
     * portlet has no initialization parameters.
     *
     * @return		an <code>Enumeration</code> of <code>String</code>
     * objects containing the names of the portlet
     * initialization parameters, or an empty <code>Enumeration</code> if the
     * portlet has no initialization parameters.
     */
    public java.util.Enumeration getInitParameterNames() {
        return initParams.keys();
    }

}

