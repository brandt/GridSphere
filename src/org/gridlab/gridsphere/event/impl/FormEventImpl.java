/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.event.impl ;

import org.gridlab.gridsphere.event.FormEvent;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

import java.util.Enumeration;

public class FormEventImpl implements FormEvent {

    protected ActionEvent event;

    public FormEventImpl(ActionEvent evt) {
        event = evt;
    }

    /**
     * Returns the name of the pressed submit button. To use this for form has to follow the convention
     * that the names of all submit-type buttons start with 'submit:' (and only those and no other elements)
     * @parameter event the actionevent
     * @return name of the button which was pressed
     */
    public String getPressedSubmitButton() {
        String result = new String();

        PortletRequest req = event.getPortletRequest();
        Enumeration enum = req.getParameterNames();
        while(enum.hasMoreElements()) {
            String name = (String)enum.nextElement();
            if (name.startsWith("submit:")) {
                Object ob = req.getParameter((String)enum.nextElement());
                if (ob!=null) {
                    result = name.substring(7);
                }
            }
        }
        return result;
    }

}
