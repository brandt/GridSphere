/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.provider.event;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.provider.portletui.beans.*;

import java.util.Map;
import java.io.IOException;

public interface FormEvent extends ActionEvent {

    public Map getTagBeans();

    public FrameBean getFrameBean(String beanID);

    public FileInputBean getFileInputBean(String beanId) throws IOException;

    public ErrorFrameBean getErrorFrameBean(String beanId);

    public CheckBoxBean getCheckBoxBean(String beanId);

    public TextFieldBean getTextFieldBean(String beanId);

    public HiddenFieldBean getHiddenFieldBean(String beanId);

    public PasswordBean getPasswordBean(String beanId);

    public TextAreaBean getTextAreaBean(String beanId);

    public TextBean getTextBean(String beanId);

    public ListBoxBean getListBoxBean(String beanId);

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
    public void printRequestParameters();

    public void store();

}
