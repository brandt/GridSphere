/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.portletui.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * An <code>ActionBean</code> is an abstract visual bean that is responsible for creating
 * <code>DefaultPortletAction</code> using a supplied <code>PortletURI</code> and is used by
 * the <code>ActionLinkBean</code> and <code>ActionSubmitBean</code>
 */
public abstract class ActionBean extends BaseComponentBean implements TagBean {

    protected String action = null;
    protected boolean isSecure = false;
    protected String portletURI = null;
    protected List<ParamBean> paramBeanList = new ArrayList<ParamBean>();
    protected String label = null;
    protected String layout = null;
    protected String anchor = null;
    protected String trackMe = null;
    protected String extUrl = null;
    protected String onClick = null;
    protected String onMouseOver = null;
    protected String onMouseOut = null;
    protected String onSubmit = null;
    protected String onReset = null;
    protected boolean useAjax = false;

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
     * Returns the action specified by the onClick attribute
     *
     * @return the action specified by the onClick attribute
     */
    public String getOnClick() {
        return onClick;
    }

    /**
     * Sets the action specified by the onClick attribute
     *
     * @param onClick the javascript action to perform
     */
    public void setOnClick(String onClick) {
        this.onClick = onClick;
    }

    /**
     * Returns the action specified by the onReset attribute
     *
     * @return the action specified by the onReset attribute
     */
    public String getOnReset() {
        return onReset;
    }

    /**
     * Sets the action specified by the onReset attribute
     *
     * @param onReset the javascript action to perform
     */
    public void setOnReset(String onReset) {
        this.onReset = onReset;
    }

    /**
     * Returns the action specified by the onMouseOver attribute
     *
     * @return onMouseOver the javascript onMouseOver event
     */
    public String getOnMouseOver() {
        return onMouseOver;
    }

    /**
     * Sets the action specified by the onMouseOver event
     *
     * @param onMouseOver the javascript onMouseOver event
     */
    public void setOnMouseOver(String onMouseOver) {
        this.onMouseOver = onMouseOver;
    }

    /**
     * Returns the action specified by the onMouseOut attribute
     *
     * @return onMouseOver the javascript onMouseOut event
     */
    public String getOnMouseOut() {
        return onMouseOut;
    }

    /**
     * Sets the action specified by the onMouseOut event
     *
     * @param onMouseOut the javascript onMouseOut event
     */
    public void setOnMouseOut(String onMouseOut) {
        this.onMouseOut = onMouseOut;
    }

    /**
     * Returns the action specified by the onSubmit attribute
     *
     * @return the action specified by the onSubmit attribute
     */
    public String getOnSubmit() {
        return onSubmit;
    }

    /**
     * Sets the action specified by the onSubmit attribute
     *
     * @param onSubmit the javascript action to perform
     */
    public void setOnSubmit(String onSubmit) {
        this.onSubmit = onSubmit;
    }

    /**
     * Returns true if this action should only retrieve the portlet fragment using AJAX (XmlHttpRequest)
     *
     * @return true if this action should only retrieve the portlet fragment using AJAX (XmlHttpRequest)
     */
    public boolean getUseAjax() {
        return useAjax;
    }

    /**
     * If true this action should only retrieve the portlet fragment using AJAX (XmlHttpRequest)
     *
     * @param useAjax true if this action should only retrieve the portlet fragment using AJAX (XmlHttpRequest)
     */
    public void setUseAjax(boolean useAjax) {
        this.useAjax = useAjax;
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
     * Returns the layout id that identifies a layout descriptor to target
     *
     * @return the layout id that identifies a layout descriptor to target
     */
    public String getLayout() {
        return layout;
    }

    /**
     * Sets the layout id that identifies a layout descriptor to target
     *
     * @param layout the layout id that identifies a layout descriptor to target
     */
    public void setLayout(String layout) {
        this.layout = layout;
    }

    /**
     * Sets a label to track this action and persist in the DB
     *
     * @param trackMe a label for this action
     */
    public void setTrackme(String trackMe) {
        this.trackMe = trackMe;
    }

    /**
     * Returns the tracking label for this action
     *
     * @return the tracking label
     */
    public String getTrackme() {
        return trackMe;
    }

    /**
     * Returns the external url that this actionlink will redirect to
     *
     * @return the external url that this actionlink will redirect to
     */
    public String getExturl() {
        return extUrl;
    }

    /**
     * Sets the external url that this actionlink will redirect to
     *
     * @param extUrl the external url that this actionlink will redirect to
     */
    public void setExturl(String extUrl) {
        this.extUrl = extUrl;
    }

    /**
     * Sets the text that should be added at the end of generated URL
     *
     * @param anchor the action link key
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * Returns the anchor used to identify text that should be added at the end of generated URL
     *
     * @return the anchor
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * Sets the action link name used to identify an anchor
     *
     * @param name the action link name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the action link name used to identify an anchor
     *
     * @return the action link name
     */
    public String getName() {
        return name;
    }

    /**
     * If secure is true, then use https, otherwise use http
     *
     * @param isSecure if true use https, otherwise use http
     */
    public void setSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

    /**
     * Returns true if this actiontag is secure e.g. https, flase otherwise
     *
     * @return true if this actiontag is secure, false otherwise
     */
    public boolean getSecure() {
        return isSecure;
    }

    /**
     * Sets the uri for the link
     *
     * @param portletURI the portlet uri
     */
    public void setPortletURI(String portletURI) {
        this.portletURI = portletURI;
    }

    /**
     * Returns the uri for the link
     *
     * @return returns the action
     */
    public String getPortletURI() {
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
    public void setParamBeanList(List<ParamBean> paramBeanList) {
        this.paramBeanList = paramBeanList;
    }

    /**
     * Returns the list of name value parameter beans
     *
     * @return the list containing <code>ActionParamBean</code>s
     */
    public List<ParamBean> getParamBeanList() {
        return paramBeanList;
    }

    /**
     * Adds an action parameter bean
     *
     * @param paramBean an action parameter bean
     */
    public void addParamBean(ParamBean paramBean) {
        paramBeanList.add(paramBean);
    }

    /**
     * Creates and adds an action parameter bean
     *
     * @param paramName  the parameter name
     * @param paramValue the parameter value
     */
    public void addParamBean(String paramName, String paramValue) {
        ParamBean paramBean = new ParamBean(paramName, paramValue);
        paramBeanList.add(paramBean);
    }

    /**
     * Removes an action parameter bean
     *
     * @param paramBean the action parameter bean
     */
    public void removeParamBean(ParamBean paramBean) {
        paramBeanList.remove(paramBean);
    }

}
