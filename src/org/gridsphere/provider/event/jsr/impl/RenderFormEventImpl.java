/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.provider.event.jsr.impl;

import org.gridsphere.portletcontainer.DefaultPortletRender;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portletui.beans.TagBean;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>RenderFormEvent</code> is sent by the portlet container when an HTTP request is
 * received that is associated with an render event
 */
public class RenderFormEventImpl extends BaseFormEventImpl implements RenderFormEvent {

    private DefaultPortletRender render;

    /**
     * Constructs an instance of RenderFormEventImpl given a render request and response
     *
     * @param render   the <code>DefaultPortletRender</code>
     * @param request  the <code>RenderRequest</code>
     * @param response the <code>RenderResponse</code>
     * @param tagBeans a collection of tag beans
     */
    public RenderFormEventImpl(DefaultPortletRender render, RenderRequest request, RenderResponse response, Map<String, TagBean> tagBeans) {
        super(request, response);
        this.render = render;
        this.tagBeans = tagBeans;
        // Unless tagBeans is null, don't recreate them
        if (tagBeans == null) {
            tagBeans = new HashMap<String, TagBean>();
            createTagBeans();
        }
        //logRequestParameters();
        //logTagBeans();
    }

    /**
     * Return the render request associated with this render event
     *
     * @return the <code>RenderRequest</code>
     */
    public RenderRequest getRenderRequest() {
        return (RenderRequest) portletRequest;
    }

    /**
     * Return the render response associated with this render event
     *
     * @return the <code>RenderResponse</code>
     */
    public RenderResponse getRenderResponse() {
        return (RenderResponse) portletResponse;
    }

    /**
     * Returns the render event
     *
     * @return the render event
     */
    public DefaultPortletRender getRender() {
        return render;
    }

}
