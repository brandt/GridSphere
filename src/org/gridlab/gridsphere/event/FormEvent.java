/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.event;

public interface FormEvent {

   /**
    * Returns the name of the pressed submit button.
    * @parameter event the actionevent
    * @return name of the button which was pressed
    */
    public String getPressedSubmitButton();
}
