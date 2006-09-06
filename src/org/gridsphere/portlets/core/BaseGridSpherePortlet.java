package org.gridsphere.portlets.core;

import org.gridsphere.provider.portlet.ActionPortlet;
import org.gridsphere.provider.event.FormEvent;
import org.gridsphere.provider.portletui.beans.MessageBoxBean;
import org.gridsphere.provider.portletui.beans.MessageStyle;

public class BaseGridSpherePortlet extends ActionPortlet {

    protected void createErrorMessage(FormEvent evt, String text) {
        MessageBoxBean msgBox = evt.getMessageBoxBean("msg");
        msgBox.setMessageType(MessageStyle.MSG_ERROR);
        String msgOld = msgBox.getValue();
        msgBox.setValue((msgOld!=null?msgOld:"")+text);
    }

    protected void createSuccessMessage(FormEvent evt, String text) {
        MessageBoxBean msg = evt.getMessageBoxBean("msg");
        msg.setValue(text);
        msg.setMessageType(MessageStyle.MSG_SUCCESS);
    }


}
