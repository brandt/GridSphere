/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * The abstract <code>BaseBean</code> is an implementation of the <code>TagBean</code> interface.
 * <code>BaseBean</code> provides the basic functionality for all visual beans.
 */
public abstract class BaseBean implements TagBean {

    protected String beanId = "";
    protected String vbName = "undefined";
    protected PortletRequest request = null;

    /**
     * Constructs default base bean
     */
    public BaseBean() {
        super();
    }

    /**
     * Constructs a base bean for the supplied visual bean type
     *
     * @param vbName a name identifying the type of visual bean
     */
    public BaseBean(String vbName) {
        this.vbName = vbName;
    }

    /**
     * Returns the bean identifier
     *
     * @return the bean identifier
     */
    public String getBeanId() {
        return this.beanId;
    }

    /**
     * Sets the bean identifier
     *
     * @param beanId the bean identifier
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public void setPortletRequest(PortletRequest request) {
        this.request = request;
    }

    public abstract String toStartString();

    public abstract String toEndString();

    public void store() {
        //System.err.println("storing bean " + getBeanKey());
        if (request != null) request.setAttribute(getBeanKey(), this);
    }

    protected String getBeanKey() {
        String compId = (String) request.getAttribute(SportletProperties.COMPONENT_ID);
        //System.err.println("in BaseBeanImpl: beankey: " + beanId + "_" + compId);
        return beanId + "_" + compId;
    }

    protected String getLocalizedText(String key) {
        Locale locale = this.request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        return bundle.getString(key);
    }


}
