/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.provider.event;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

public interface FormEvent extends ActionEvent {

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
     * @param request requestobject where the bean was stored (in the session of the request)
     * @return updated elementbean
     */
    public Object getTagBean(String name, PortletRequest request);

    /**
     * For debugging
     */
    public void printRequestParameter(PortletRequest req);

    /**
     * Retrieves bean from the current session.
     * @param beanname name of the bean
     * @return bean
     */
    public Object getStoredTagBean(String beanname);

}
