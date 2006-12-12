/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: RenderFormEvent.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.event.jsr;

import org.gridsphere.portletcontainer.DefaultPortletRender;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * A <code>RenderFormEvent</code> is sent by the portlet container when an HTTP request is
 * received that is associated with an render event
 */
public interface RenderFormEvent extends FormEvent {

    /**
     * Returns the render event
     *
     * @return the render event
     */
    public DefaultPortletRender getRender();

    /**
     * Return the render request associated with this render event
     *
     * @return the <code>RenderRequest</code>
     */
    public RenderRequest getRenderRequest();

    /**
     * Return the render response associated with this render event
     *
     * @return the <code>RenderResponse</code>
     */
    public RenderResponse getRenderResponse();

}
