/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletRequest;

/**
 * The <code>TagBean</code> interface describes the base methods provided by all visual ui beans
 */
public interface TagBean {

    /**
     * Returns the bean identifier
     *
     * @return the bean identifier
     */
    public String getBeanId();

    /**
     * Sets the bean identifier
     *
     * @param beanId the bean identifier
     */
    public void setBeanId(String beanId);

    /**
     * Sets the associated portlet request for this bean
     *
     * @param request the portlet request
     */
    public void setPortletRequest(PortletRequest request);

    /**
     * Returns the HTML representation of the bean
     *
     * @return html string representing the object
     */
    public String toStartString();

    /**
     * Returns the HTML representation of the bean
     *
     * @return html string representing the object
     */
    public String toEndString();

    /**
     * Stores this bean in the portlet request
     */
    public void store();

}
