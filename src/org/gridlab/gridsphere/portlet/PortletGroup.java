/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.portlet.impl.SportletGroup;


public interface PortletGroup {

    public static final PortletGroup BASE = SportletGroup.BASE;
    public static final PortletGroup SUPER = SportletGroup.SUPER;

    public String getName();

    public String getID();

    public String toString();

}
