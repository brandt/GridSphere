/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import javax.servlet.http.HttpServletRequest;

public class TabBean extends BeanContainer {

    protected String jspPage;
    protected boolean isActive = false;
    protected String title = "";

    /**
     * Constructs a default table row bean
     */
    public TabBean() {
        super();
    }

    /**
     * Constructs a default table row bean
     */
    public TabBean(BaseComponentBean compBean) {
        super();
        this.addBean(compBean);
    }

    /**
     * Constructs a table row bean from a supplied portlet request and bean identifier
     *
     * @param req    the portlet request
     * @param beanId the bean identifier
     */
    public TabBean(HttpServletRequest req, String beanId) {
        super();
        this.request = req;
        this.beanId = beanId;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getActive() {
        return isActive;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPage(String jspPage) {
        this.jspPage = jspPage;
    }

    public String getPage() {
        return jspPage;
    }

    public String toStartString() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        return sb.toString();
    }

}
