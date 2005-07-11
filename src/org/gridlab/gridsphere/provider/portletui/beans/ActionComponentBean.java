package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import javax.servlet.http.HttpServletRequest;
import javax.portlet.PortletRequest;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 * <p>
 * Includes jsp pages from any web application.
 */

public class ActionComponentBean extends IncludeBean {

    private transient static PortletLog log = SportletLog.getInstance(ActionComponentBean.class);
    protected String activeCompId = "";

    /**
     * Constructs default include bean
     */
    public ActionComponentBean() {
        super();
    }

    /**
     * Constructs an include bean
     */
    public ActionComponentBean(String beanId) {
        super(beanId);
    }

    /**
     * Constructs an include bean
     */
    public ActionComponentBean(Object req, String beanId) {
        if (req instanceof HttpServletRequest) {
            this.request = (HttpServletRequest)req;
        }
        if (req instanceof PortletRequest) {
            this.portletRequest = (PortletRequest)req;
        }
        this.beanId = beanId;
    }

    public String getActiveComponentId() {
        return activeCompId;
    }

    public void setActionComponentId(String compId) {
        this.activeCompId = compId;
    }

    public void store() {
        log.debug("Storing action component bean " + getBeanKey());
        super.store();
    }
}
