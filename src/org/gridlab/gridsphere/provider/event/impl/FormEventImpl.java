/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 *
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.event.impl;

import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.event.FormEvent;

import java.util.HashMap;
import java.util.Map;

/*
 * The <code>FormEventImpl</code> provides methods for creating/retrieving visual beans
 * from the <code>PortletRequest</code>
 */

public class FormEventImpl extends BaseFormEventImpl implements FormEvent {

    protected transient static PortletLog log = SportletLog.getInstance(FormEventImpl.class);

    protected ActionEvent event;
    protected PortletRequest portletRequest;
    protected PortletResponse portletResponse;

    protected FormEventImpl() {

    }

    /**
     * Constructs a FormEventImpl from a portlet request, a portlet response, and a collection of visual beans
     *
     * @param request  the portlet request
     * @param response the portlet response
     * @param tagBeans a collection of tag beans
     */
    public FormEventImpl(PortletRequest request, PortletResponse response, Map tagBeans) {
        super(request, response);
        //log.debug("FormEventImpl()");
        this.portletRequest = request;
        this.portletResponse = response;
        this.tagBeans = tagBeans;
        // Unless tagBeans is null, don't recreate them
        if (tagBeans == null) {
            tagBeans = new HashMap();
            createTagBeans(request);
        }
        logRequestParameters();

        logTagBeans();
    }

    /**
     * Constructs a FormEventImpl from a supplied action event
     *
     * @param evt the action event
     */
    public FormEventImpl(ActionEvent evt) {
        super(evt.getPortletRequest(), evt.getPortletResponse());
        //log.debug("FormEventImpl()");
        this.event = evt;
        portletRequest = evt.getPortletRequest();
        portletResponse = evt.getPortletResponse();
        // Only create tag beans from request when initialized with action event
        createTagBeans(evt.getPortletRequest());

        logRequestParameters();

        logTagBeans();
    }

    /**
     * Returns the portlet request
     *
     * @return the portlet request
     */
    public PortletRequest getPortletRequest() {
        return portletRequest;
    }

    /**
     * Return the portlet response
     *
     * @return the portlet response
     */
    public PortletResponse getPortletResponse() {
        return portletResponse;
    }

    /**
     * Return the portlet action
     *
     * @return the portlet action
     */
    public DefaultPortletAction getAction() {
        if (event == null) {
            return null;
        }
        return event.getAction();
    }

    /**
     * Return the portlet action as a String
     *
     * @return the portlet action as a String
     */
    public String getActionString() {
        if (event == null) {
            return null;
        }
        return event.getActionString();
    }

}