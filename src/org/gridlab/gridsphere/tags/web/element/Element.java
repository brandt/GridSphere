/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface Element {

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
     * Returns the HTML reprensetation of the element
     * @return html string presenting the object
     */
    public String toString();


}
