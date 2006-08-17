/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.portal;

import java.io.IOException;


/**
 * Portal configuration service is used to manage portal administrative settings
 */
public interface PortalConfigService {

    public String getProperty(String key);

    public void setProperty(String key, String value);

    public void storeProperties() throws IOException;

}
