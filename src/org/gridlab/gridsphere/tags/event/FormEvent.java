/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

public interface FormEvent extends ActionTagEvent {

   /**
    * Returns the name of the pressed submit button.
    *
    * @return name of the button which was pressed
    */
    public String getSubmitButtonName();

}
