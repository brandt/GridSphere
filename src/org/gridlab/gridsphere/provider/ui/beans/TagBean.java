/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public interface TagBean {

    /**
     * Gets the ID of the beans
     * @return the id of the beans
     */
    public String getId();

    /**
     * Sets the ID od the bean.
     * @param id id of the bean
     */
    public void setId(String id);

    /**
     * Sets the color of the beans
     * @param color the color of the beans
     */
    public void setColor(String color);

    /**
     * Gets the color of the beans
     * @return the color of the beans
     */
    public String getColor();

    /**
     * Gets the font of the beans.
     * @return font of the beans
     */
    public String getFont();

    /**
     * Sets the font of the beans.
     * @param font the font to set
     */
    public void setFont(String font);

    /**
     * Returns the backgroundcolor of the beans.
     * @return the backgroundcolor
     */
    public String getBackgroundcolor();

    /**
     * Sets the backgoundcolor of the beans.
     * @param backgroundcolor the backgroundcolor to be set
     */
    public void setBackgroundcolor(String backgroundcolor);

    /**
     * Returns the CSS style name of the beans.
     * @return the name of the css style
     */
    public String getCssStyle();

    /**
     * Sets the CSS style of the beans.
     * @param style css style name to set for the beans
     */
    public void setCssStyle(String style);

    /**
     * Store the bean under the id 'id' in the portletrequest as well as in the users session.
     * If an object in session/request exists under this name it gets replaced with this bean.
     * @param id the id of the object
     * @param request the portletrequest to store the data in
     */
    public void store(String id, PortletRequest request);

    /**
     * Returns the HTML reprensetation of the beans
     * @return html string presenting the object
     */
    public String toString();




}
