/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.beans.TagBean;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;


/**
 * <code>BaseElementBean</code> is an implementation of the TagBean interface.
 * <code>BaseElementBean</code> provides the basic functionality for all ui beans.
 */
public abstract class BaseBeanImpl implements TagBean {

    protected String beanId = "";
    protected PortletRequest request = null;

    public BaseBeanImpl() {
        super();
    }

    /**
     * Gets the ID.
     * @return id of the bean
     */
    public String getBeanId() {
        return this.beanId;
    }

    /**
     * Sets the ID od the bean.
     * @param beanId the id of the bean
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public void setPortletRequest(PortletRequest request)  {
        this.request = request;
    }

    public String toString() {
        return "";
    }

    protected void store(Object object) {
        if (!beanId.equals("")) {
            System.err.println("saving " + beanId + " into session");
            if (request != null) {
                request.getSession().setAttribute(getBeanKey(), object);
            }
        }
    }

    protected String getBeanKey() {
        String compId = (String)request.getAttribute(GridSphereProperties.COMPONENT_ID);
        System.err.println("in BaseBeanImpl: beankey: " + beanId + "_" + compId);
        return beanId + "_" + compId;
    }
}
