/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: WindowEventImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.layout.event.impl;

import org.gridsphere.layout.event.PortletWindowEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.EventObject;

/**
 * A <code>WindowEventImpl</code> is the <code>WindowEvent</code> implementation
 * that is responsible for handling window action events e.g. <code>MINIMIZED</code>,
 * <code>MAXIMIZED</code> or <code>RESTORED</code>.
 */
public class PortletWindowEventImpl extends EventObject implements PortletWindowEvent {

    private int event = -1;

    /**
     * Constructs an instance of <code>WindowEventImpl</code> with a provided
     * portlet request and response
     *
     * @param req         the <code>PortletRequests</code>
     * @param windowEvent the window event id
     */
    public PortletWindowEventImpl(HttpServletRequest req, int windowEvent) {
        super(req);
        this.event = windowEvent;
    }

    /**
     * Returns the identifier of the current window event
     *
     * @return the window event identifier
     */
    public int getEventId() {
        return event;
    }

}
