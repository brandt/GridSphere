/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

public interface Disableable extends TagBean {

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
