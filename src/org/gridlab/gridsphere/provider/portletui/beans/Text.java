/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public interface Text extends TagBean {

    /**
     * Gets the text of the bean.
     * @return text of the bean
     */
    public String getText();

    /**
     * Sets the text of the bean
     * @param text the text to be set
     */
    public void setText(String label);


}
