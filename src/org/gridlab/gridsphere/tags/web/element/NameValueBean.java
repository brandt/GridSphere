/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface NameValueBean extends ElementBean {

    /**
     * Sets the value of the bean.
     * @param value of the bean
     */
    public void setValue(String value);

    /**
     * Gets the vale of the bean.
     * @return the value of the bean
     */
    public String getValue();

    /**
     * Sets the name of the bean
     * @param name name of the bean
     */
    public void setName(String name);

    /**
     * Gets the name of the bean.
     * @return the name of the bean
     */
    public String getName();

    /**
     * Return true/false if the bean is disabled.
     * @return true/false if the bean is disabled
     */
    public boolean isDisabled();

    /**
     * Sets the bean to disabled depedinding on <code>flag</code>
     * @param flag status if bean should be disabled
     */
    public void setDisabled(boolean flag);

    /**
     * Updates the values of the bean.
     * @param values array of strings representaing the updates
     */
    public void update(String[] values);

}
