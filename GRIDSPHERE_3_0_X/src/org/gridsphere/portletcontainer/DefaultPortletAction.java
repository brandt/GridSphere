/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: DefaultPortletAction.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.portletcontainer;

import java.io.Serializable;

/**
 * The <code>DefaultPortletAction</code> is a portlet action with default parameters.
 * You can use this portlet action to pass parameters in your action or create your own portlet action.
 * This default implementation demonstrates how to implement it.
 */
public final class DefaultPortletAction extends DefaultPortletPhase implements Serializable {

    public static final String DEFAULT_PORTLET_ACTION = "gs_action";

    /**
     * Constructs an instance of DefaultPortletAction with the provided name
     *
     * @param name the name of this action
     */
    public DefaultPortletAction(String name) {
        super(name);
    }


    public String toString() {
        return super.toString(DEFAULT_PORTLET_ACTION);
    }
}
