/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface Valueable extends Nameable {

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
     * Return true/false if the bean is disabled.
     * @return true/false if the bean is disabled
     */
    public boolean isDisabled();

    /**
     * Sets the bean to disabled depedinding on <code>flag</code>
     * @param flag status if bean should be disabled
     */
    public void setDisabled(boolean flag);


}
