/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.provider.event;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.Map;

public interface FormEvent extends ActionEvent {

    public Map getTagBeans();

    public FrameBean getFrameBean(String beanID);

    public ErrorFrameBean getErrorFrameBean(String beanID);

    public CheckBoxBean getCheckBoxBean(String beanId);

    public TextFieldBean getTextFieldBean(String beanId);

    public PasswordBean getPasswordBean(String beanId);

    public TextBean getTextBean(String beanId);


   /**
    * Returns the name of the pressed submit button.
    * @return name of the button which was pressed
    */
    public String getSubmitButtonName();

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getTagBean(String name);

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.
     * @param name name of the bean
     * @return updated elementbean
     */
    public org.gridlab.gridsphere.provider.portletui.beans.TagBean getNewTagBean(String name);

    /**
     * For debugging
     */
    public void printRequestParameter();

    public void store();

}
