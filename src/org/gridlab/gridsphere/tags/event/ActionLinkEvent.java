/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event;


import java.util.List;

public interface ActionLinkEvent extends ActionTagEvent {

    /**
     * Return the list of action parameter beans
     *
     * @return the list of action parameter beans
     */
    public List getActionParamBeans();

    /**
     * Return the action parameter value associated with the action name
     *
     * @param name the action parameter name
     * @return the action parameter value
     */
    public String getActionParamValue(String name);

    /**
     * Returns the action name . This is the same as
     * {@link org.gridlab.gridsphere.portlet.DefaultPortletAction#getName}
     *
     * @return the action name
     */
    public String getActionName();

}
