/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

import org.gridlab.gridsphere.portlet.PortletRequest;

import javax.servlet.http.HttpSession;

public abstract class BaseElementBean implements ElementBean {

    protected String id = new String();
    protected String color;
    protected String backgroundcolor;
    protected String cssStyle;
    protected String font;


    public BaseElementBean() {
        super();
    }

    /**
     * Returns the id with append _ if this != "". This is used for generating unqiue html names for elements.
     * @return idref
     */
    protected String getIdRef() {
        if (!id.equals("")) {
            return id+"_";
        } else {
            return "";
        }
    }

    /**
     * Gets the ID.
     * @return id of the element
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the ID od the bean.
     * @param id id of the bean
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the color of the element.
     * @param color set the color of the element
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the color of the element.
     * @return color of the element
     */
    public String getColor() {
        return color;
    }

    /**
     * Gets the font of the element.
     * @return font of the element
     */
    public String getFont() {
        return font;
    }

    /**
     * Sets the font of the element.
     * @param font the font to set
     */
    public void setFont(String font) {
        this.font = font;
    }

    /**
     * Returns the backgroundcolor of the element.
     * @return the backgroundcolor
     */
    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    /**
     * Sets the backgoundcolor of the element.
     * @param backgroundcolor the backgroundcolor to be set
     */
    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    /**
     * Returns the CSS style name of the element.
     * @return the name of the css style
     */
    public String getCssStyle() {
        return cssStyle;
    }

    /**
     * Sets the CSS style of the element.
     * @param style css style name to set for the element
     */
    public void setCssStyle(String style) {
        this.cssStyle = style;
    }

    /**
     * Store the bean under the id 'id' in the portletrequest as well as in the users session.
     * If an object in session/request exists under this name it gets replaced with this bean.
     * @param id the id of the object
     * @param request the portletrequest to store the data in
     */
    public void store(String id, PortletRequest request) {
        this.id = id;
        request.setAttribute(id, this);
        HttpSession session = request.getSession();
        session.setAttribute(id, this);
    }

}
