/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

import org.gridlab.gridsphere.portlet.PortletRequest;

public interface ElementBean {

    public String getId();

    public void setId(String id);

    /**
     * Sets the color of the element
     * @param color the color of the element
     */
    public void setColor(String color);

    /**
     * Gets the color of the element
     * @return the color of the element
     */
    public String getColor();

    public String getFont();

    public void setFont(String font);

    public String getBackgroundcolor();

    public void setBackgroundcolor(String backgroundcolor);

    public String getCssStyle();

    public void setCssStyle(String style);

    /**
     * Store the bean under the id 'id' in the portletrequest as well as in the users session.
     * If an object in session/request exists under this name it gets replaced with this bean.
     * @param id the id of the object
     * @param request the portletrequest to store the data in
     */
    public void store(String id, PortletRequest request);

    /**
     * Returns the HTML reprensetation of the element
     * @return html string presenting the object
     */
    public String toString();


}
