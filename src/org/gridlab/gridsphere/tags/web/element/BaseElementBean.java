/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

import org.gridlab.gridsphere.portlet.PortletRequest;

import javax.servlet.http.HttpSession;

public abstract class BaseElementBean implements ElementBean {

    protected String id;
    protected String color;
    protected String backgroundcolor;
    protected String cssStyle;
    protected String font;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getBackgroundcolor() {
        return backgroundcolor;
    }

    public void setBackgroundcolor(String backgroundcolor) {
        this.backgroundcolor = backgroundcolor;
    }

    public String getCssStyle() {
        return cssStyle;
    }

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
        request.setAttribute(id, this);
        HttpSession session = request.getSession();
        session.setAttribute(id, this);
    }
}
