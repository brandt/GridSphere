/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import java.util.ArrayList;
import java.util.List;


/**
 * The PortletLayout is responsible for constructing a layout appropriate
 * to the user's layout preferences.
 */
public interface PortletLayout extends ComponentLifecycle {

    public void setPortletComponents(ArrayList components);

    public List getPortletComponents();

    public void addPortletComponent(PortletComponent component);

    public void removePortletComponent(PortletComponent component);

}


