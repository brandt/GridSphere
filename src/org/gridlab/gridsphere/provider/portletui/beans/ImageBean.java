/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * A <code>ImageBean</code> represents an image element
 */
public class ImageBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "im";
    public String src = "";
    public String alt = new String();
    public String title = new String();

    /**
     * Constructs a default image bean
     */
    public ImageBean() {
        super(NAME);
    }

    /**
     * Constructs an image bean using a supplied  bean identifier
     *
     * @param beanId the bean identifier
     */
    public ImageBean(String beanId) {
        super(beanId);
    }

    /**
     * Constructs a URL image bean from a supplied portlet request and bean identifier
     *
     * @param req the portlet request
     * @param beanId the bean identifier
     */
    public ImageBean(PortletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
    }

    /**
     * Returns the image source
     *
     * @return the image source
     */
    public String getSrc() {
        return src;
    }

    /**
     * Sets the image source
     *
     * @param src the image source
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Returns the image alt tag
     *
     * @return the image alt tag
     */
    public String getAlt() {
        return alt;
    }

    /**
     * Sets the image alt tag
     *
     * @param alt the image alt tag
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * Returns the image title
     *
     * @return the image title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the image title
     *
     * @param title the image title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String toStartString() {
        return "<img src=\""+this.src+"\" alt=\""+alt+"\" title=\""+this.title+"\"/>";
    }

    public String toEndString() {
        return "";
    }
}
 


