/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.tags.event.impl;


import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.tags.event.ActionTagEvent;
import org.gridlab.gridsphere.tags.web.element.NameBean;

import javax.servlet.http.HttpSession;

public class ActionTagEventImpl implements ActionTagEvent {

    protected ActionEvent event;
    protected PortletRequest request;
    protected PortletResponse response;

    public ActionTagEventImpl(ActionEvent evt) {
        event = evt;
        request = evt.getPortletRequest();
    }

    public PortletRequest getPortletRequest() {
        return request;
    }

    public PortletResponse getPortletResponse() {
        return response;
    }

    public  DefaultPortletAction getAction() {
        return event.getAction();
    }

    protected Object getBeanFromSession(String name, PortletRequest request) {
        HttpSession session = request.getSession();
        NameBean bean = (NameBean) session.getAttribute(name);
        return bean;
    }

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.
     * @param name name of the bean
     * @param request requestobject where the bean was stored (in the session of the request)
     * @return updated elementbean
     */
    protected Object getTagBean(String name, PortletRequest request) {
        HttpSession session = request.getSession();
        NameBean bean = (NameBean) getBeanFromSession(name, request);
        System.out.println("Getting Bean " + name + " from Session");
        //if (checkParameterName("gstag:"+bean.getName())) {
        String[] values = request.getParameterValues("gstag:" + bean.getName());
        //if (values.length>0) {
        System.out.println("Updated bean: " + bean.getName());
        bean.update(values);
        session.setAttribute(name, bean);
        //}
        //}
        return bean;
    }

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.
     * @param name name of the bean
     * @return updated elementbean
     */
    public Object getTagBean(String name) {
        PortletRequest request = event.getPortletRequest();
        return getTagBean(name, request);
    }

}
