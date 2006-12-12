package org.gridsphere.portletcontainer;

import java.io.Serializable;

/**
 * The <code>DefaultPortletRender</code> is a render event with default parameters.
 */
public final class DefaultPortletRender extends DefaultPortletPhase implements Serializable {

    public static final String DEFAULT_PORTLET_RENDER = "gs_render";

    /**
     * Constructs an instance of DefaultPortletRender with the provided name
     *
     * @param name the name of the render event
     */
    public DefaultPortletRender(String name) {
        super(name);
    }

    public String toString() {
        return super.toString(DEFAULT_PORTLET_RENDER);
    }
}
