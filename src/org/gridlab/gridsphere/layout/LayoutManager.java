/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.List;
import java.util.Vector;


/**
 * The LayoutManager is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 */
public interface LayoutManager extends PortletLifecycle {

    public void setPortletComponents(Vector components);

    public List getPortletComponents();

}


