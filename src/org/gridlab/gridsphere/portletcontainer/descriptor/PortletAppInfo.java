/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

public interface PortletAppInfo {

    public String getHREF();

    public String getID();

    public String getPortletName();

    public int getExpiresCache();

    public boolean getExpiresShared();

    public String[] getAllowed();

    public String[] getSupports();

}
