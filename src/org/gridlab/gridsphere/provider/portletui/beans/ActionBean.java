/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletURI;

import java.util.ArrayList;
import java.util.List;

/**
 * An <code>ActionBean</code> is an abstract visual bean that is responsible for creating
 * <code>DefaultPortletAction</code> using a supplied <code>PortletURI</code> and is used by
 * the <code>ActionLinkBean</code> and <code>ActionSubmitBean</code>
 */
public abstract class ActionBean extends BaseComponentBean implements TagBean {

    protected String action = null;
    protected PortletURI portletURI = null;
    protected List paramBeanList = new ArrayList();
    protected String label = null;


    /**
     * Constructs default action bean
     */
    public ActionBean() {
    }

    /**
     * Constructs action bean with the supplied name
     *
     * @param name an identifying name
     */
    public ActionBean(String name) {
        super(name);
    }

    /**
     * Constructs action bean with the supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public ActionBean(PortletRequest req, String beanId) {
        this.request = req;
        this.beanId = beanId;
    }

    /**
     * Sets the label identified with the portlet component to link to
     *
     * @param label the action link key
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns the label identified with the portlet component to link to
     *
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the uri for the link
     *
     * @param portletURI the portlet uri
     */
    public void setPortletURI(PortletURI portletURI) {
        this.portletURI = portletURI;
    }

    /**
     * Returns the uri for the link
     *
     * @return returns the action
     */
    public PortletURI getPortletURI() {
        return portletURI;
    }

    /**
     * Sets the action for the link
     *
     * @param action the action name
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Returns the action name
     *
     * @return returns the action name
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the list of name value parameter beans
     *
     * @param paramBeanList a list containing <code>ActionParamBean</code>s
     */
    public void setParamBeanList(List paramBeanList) {
        this.paramBeanList = paramBeanList;
    }

    /**
     * Returns the list of name value parameter beans
     *
     * @return the list containing <code>ActionParamBean</code>s
     */
    public List getParamBeanList() {
        return paramBeanList;
    }

    /**
     * Adds an action parameter bean
     *
     * @param paramBean an action parameter bean
     */
    public void addParamBean(ActionParamBean paramBean) {
        paramBeanList.add(paramBean);
    }

    /**
     * Creates and adds an action parameter bean
     *
     * @param paramName the parameter name
     * @param paramValue the parameter value
     */
    public void addParamBean(String paramName, String paramValue) {
        ActionParamBean paramBean = new ActionParamBean(paramName, paramValue);
        paramBeanList.add(paramBean);
    }

    /**
     * Removes an action parameter bean
     *
     * @param paramBean the action parameter bean
     */
    public void removeParamBean(ActionParamBean paramBean) {
        paramBeanList.remove(paramBean);
    }

}
