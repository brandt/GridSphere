/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: BaseBean.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.impl.SportletProperties;

import javax.portlet.RenderResponse;
import javax.portlet.RenderRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * The abstract <code>BaseBean</code> is an implementation of the <code>TagBean</code> interface.
 * <code>BaseBean</code> provides the basic functionality for all visual beans.
 */
public abstract class BaseBean implements TagBean {

    protected String beanId = "";
    protected String vbName = "undefined";
    protected Locale locale = null;
    protected Map params = new HashMap();
    public RenderResponse renderResponse;
    public RenderRequest renderRequest;

    public RenderResponse getRenderResponse() {
        return renderResponse;
    }

    public void setRenderResponse(RenderResponse renderResponse) {
        this.renderResponse = renderResponse;
    }


    public RenderRequest getRenderRequest() {
        return renderRequest;
    }

    public void setRenderRequest(RenderRequest renderRequest) {
        this.renderRequest = renderRequest;
    }

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

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void removeParam(String name) {
        params.remove(name);
    }

    protected String createTagName(String name) {
        String sname = "";
        String pname = (name == null) ? "" : name;
        String compId = (String)params.get(SportletProperties.GP_COMPONENT_ID);
        if (beanId.equals("")) return pname;
        if (compId == null) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        } else {
            String cid = (String)params.get(SportletProperties.COMPONENT_ID);
            sname = "ui_" + vbName + "_" + compId + "%" + beanId + "_" + pname + cid;
        }
        return sname;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
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


    public abstract String toStartString();

    public abstract String toEndString();

    public String getBeanKey() {
        String cid = (String)params.get(SportletProperties.COMPONENT_ID);
        String compId = (String)params.get(SportletProperties.GP_COMPONENT_ID);

        String beanKey = null;
        if (compId == null) {
            beanKey = beanId + "_" + cid;
        } else {
            beanKey = compId + "%" + beanId + "_" + cid;
        }
        System.err.println("getBeanKey(" + beanId + ") = " + beanKey);
        return beanKey;
    }

    protected String getLocalizedText(String key) {
        return getLocalizedText(key, "gridsphere.resources.Portlet");
    }

    protected String getLocalizedText(String key, String base) {
        ResourceBundle bundle = ResourceBundle.getBundle(base, locale);
        return bundle.getString(key);
    }

}
