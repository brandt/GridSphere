package org.gridsphere.provider.portletui.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 * <p>
 * Includes jsp pages from any web application.
 */

public class ActionComponentBean extends IncludeBean {

    private transient static Log log = LogFactory.getLog(ActionComponentBean.class);
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
