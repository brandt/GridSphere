/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

import javax.servlet.http.HttpSession;

public abstract class BaseElementBean implements TagBean {

    protected String id = new String();
    protected String color = new String();
    protected String backgroundcolor = new String();
    protected String cssStyle = new String();
    protected String font = new String();


    public BaseElementBean() {
        super();
    }

    /**
     * Gets the ID.
     * @return id of the beans
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

    /**
     * Store the bean under the id 'id' in the portletrequest as well as in the users session.
     * If an object in session/request exists under this name it gets replaced with this bean.
     * @param id the id of the object
     * @param request the portletrequest to store the data in
     */
    public void store(String id, PortletRequest request) {
        store(id, request, this);
    }

    private void store(String id, PortletRequest request, Object ob) {
        System.err.println("Storing bean with [" + id + "][" + ob.getClass().getName());
        this.id = id;
        request.setAttribute(id, ob);
        HttpSession session = request.getSession();
        session.setAttribute(id, ob);
    }

    /**
     * Returns the String prepended to all form beans names in the html code
     * @return tagidentifier
     */
    protected String getTagName() {
        return "gstag:";
    }

    protected String getCSS(String text) {

        if (cssStyle.equals("") && color.equals("") && font.equals("") && backgroundcolor.equals("")) {
            return text;
        }
        String css = "<span ";
        if (!cssStyle.equals("")) {
            css = css + " class='" + cssStyle + "' ";
        }
        css = css + " style='";
        if (!color.equals("")) {
            css = css + "color:" + color + ";";
        }
        if (!font.equals("")) {
            css = css + "font:" + font + ";";
        }
        if (!backgroundcolor.equals("")) {
            css = css + "background:" + backgroundcolor + ";";
        }

        css = css + "'>";
        css = css + text + "</span>";
        return css;
    }

}
