/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

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
