/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal;



/**
 * Portal configuration service is used to manage portal administrative settings
 */
public interface PortalConfigService {

    public void savePortalConfigSettings(PortalConfigSettings configSettings);

    public PortalConfigSettings getPortalConfigSettings();

}
