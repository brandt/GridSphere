/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRequestImpl;
import org.gridlab.gridsphere.portlet.impl.SportletResponseImpl;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.ServletParsingService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletParsingServiceImpl implements PortletServiceProvider, ServletParsingService {

    private static PortletLog log = SportletLog.getInstance(ServletParsingServiceImpl.class);

    public void init(PortletServiceConfig config) {
        log.info("in init()");
    }

    public void destroy() {
        log.info("in destroy()");
    }

    public PortletRequest getPortletRequest(HttpServletRequest request) {
        SportletRequestImpl req = new SportletRequestImpl(request);

        // XXX: FIX THIS -- NEED TO PASS ON PORTLETSETTINGS FROM REGISTRY
        req.setPortletSettings(null);



        // Uncomment to look at request header
        //sportletRequest.logRequest();

        // Configure user
        /*
        if (req.getUser()) {

        }
        */
        return (PortletRequest) req;
    }

    public PortletResponse getPortletResponse(HttpServletResponse res) {
        SportletResponseImpl sportletResponse = new SportletResponseImpl(res);
        return (PortletResponse) sportletResponse;
    }

    public void putPortletRequest(PortletRequest req) {

    }

}
