/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.container.uniqueid.impl;

import org.gridlab.gridsphere.portlet.service.PortletService;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.services.container.uniqueid.UniqueIDService;

public class UniqueIDServiceImpl implements UniqueIDService, PortletServiceProvider {

    protected static int counter;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {}

    public void destroy() {}

    public int getUniqueIDAsInteger() {
        int current;
        synchronized(UniqueIDServiceImpl.class)
        {
            current = counter++;
        }
        String id = Integer.toString(current);

        // If you manage to get more than 100 million of ids, you'll
        // start getting ids longer than 8 characters.
        if(current < 100000000)
        {
            id = ("00000000"+id).substring(id.length());
        }
        return new Integer(id).intValue();
    }

    public String getUniqueIDAsString() {
        int current;
        synchronized(UniqueIDServiceImpl.class)
        {
            current = counter++;
        }
        String id = Integer.toString(current);

        // If you manage to get more than 100 million of ids, you'll
        // start getting ids longer than 8 characters.
        if(current < 100000000)
        {
            id = ("00000000"+id).substring(id.length());
        }
        return id;
    }

}
