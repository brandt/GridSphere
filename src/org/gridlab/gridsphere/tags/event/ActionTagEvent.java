/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;

public interface ActionTagEvent extends ActionEvent {

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface
     *
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getTagBean(String name);

}
