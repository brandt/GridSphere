/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlets.grid.job;

import org.gridlab.gridsphere.portlet.PortletConfig;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletSettings;
import org.gridlab.gridsphere.provider.AbstractActionPortlet;

import javax.servlet.UnavailableException;
import java.io.IOException;
import java.io.PrintWriter;

public class JobManagerPortlet extends AbstractActionPortlet {

    public void init(PortletConfig config) throws UnavailableException {
        super.init(config);
        setPortletBeanName("jobManagerBean");
        setPortletBeanClass(JobManagerBean.class);
        getPortletLog().info("Exiting init()");
    }

    public void initConcrete(PortletSettings settings) throws UnavailableException {
        super.initConcrete(settings);
        getPortletLog().info("Exiting initConcrete()");
    }
}
