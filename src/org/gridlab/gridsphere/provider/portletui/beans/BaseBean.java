/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.beans.TagBean;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;


/**
 * <code>BaseElementBean</code> is an implementation of the TagBean interface.
 * <code>BaseElementBean</code> provides the basic functionality for all ui beans.
 */
public abstract class BaseBean implements TagBean {

    protected String beanId = "";
    protected PortletRequest request = null;
    protected String color = "";
    protected String backgroundcolor = "";
    protected String cssStyle = "";
    protected String font = "";

    public BaseBean() {
        super();
    }

    /**
     * Gets the ID.
     * @return id of the bean
     */
    public String getBeanId() {
        return this.beanId;
    }

    /**
     * Sets the ID od the bean.
     * @param beanId the id of the bean
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    /**
     * Sets the color of the beans.
     * @param color set the color of the beans
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the color of the beans.
     * @return color of the beans
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the font of the beans.
     * @return font of the beans
     */
    public String getFont() {
        return font;
    }

    /**
     * Sets the font of the beans.
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     * Returns the backgroundcolor of the beans.
     * @return the backgroundcolor
     */
    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    /**
     * Sets the backgoundcolor of the beans.
     * @param backgroundcolor the backgroundcolor to be set
     */
    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    /**
     * Returns the CSS style name of the beans.
     * @return the name of the css style
     */
    public String getCssStyle() {
        return cssStyle;
    }

    /**
     * Sets the CSS style of the beans.
     * @param style css style name to set for the beans
     */
    public void setCssStyle(String style) {
        this.cssStyle = style;
    }

    public void setPortletRequest(PortletRequest request)  {
        this.request = request;
    }

    public String toString() {
        return "";
    }

    protected void store(Object object) {
        if (!beanId.equals("")) {
            System.err.println("saving " + beanId + " into session");
            if (request != null) {
                request.getSession().setAttribute(getBeanKey(), object);
            }
        }
    }

    protected String getBeanKey() {
        String compId = (String)request.getAttribute(GridSphereProperties.COMPONENT_ID);
        System.err.println("in BaseBeanImpl: beankey: " + beanId + "_" + compId);
        return beanId + "_" + compId;
    }
}
