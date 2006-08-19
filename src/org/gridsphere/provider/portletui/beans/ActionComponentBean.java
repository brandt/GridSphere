package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.PortletLog;
import org.gridsphere.portlet.impl.SportletLog;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentBean.java 4496 2006-02-08 20:27:04Z wehrens $
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

    public String getActiveComponentId() {
        return activeCompId;
    }

    public void setActionComponentId(String compId) {
        this.activeCompId = compId;
    }

    public void store() {
        log.debug("Storing action component bean " + getBeanKey());
        //super.store();
    }
}
