/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

public interface TagBean {

    /**
     * Gets the ID of the beans
     * @return the id of the beans
     */
    public String getBeanId();

    /**
     * Sets the ID od the bean.
     * @param id id of the bean
     */
    public void setBeanId(String id);

    public void setPortletRequest(PortletRequest request);

    /**
     * Returns the HTML reprensetation of the beans
     * @return html string presenting the object
     */
    public String toStartString();

    /**
     * Returns the HTML reprensetation of the beans
     * @return html string presenting the object
     */
    public String toEndString();

    public void store();

}
