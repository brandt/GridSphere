/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

import org.gridlab.gridsphere.portlet.impl.SportletGroup;

public interface PortletGroup {

    public static final PortletGroup BASE = new SportletGroup(SportletGroup.BASE_GROUP);
    public static final PortletGroup SUPER = new SportletGroup(SportletGroup.SUPER_GROUP);

    public boolean isBaseGroup();

    public boolean isSuperGroup();

    public String getName();

    public String getID();

    public boolean equals(Object object);

    public String toString();

}
