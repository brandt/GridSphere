/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 *
 */
public class URLImageBean extends BaseComponentBean implements TagBean {
    private static PortletLog log = SportletLog.getInstance(URLImageBean.class);

    public static final String NAME = "im";
    public String url = "";
    public String alt = new String();
    public String title = new String();

    public URLImageBean() {
        super(NAME);
    }

    public URLImageBean(String beanId) {
        super(beanId);
    }

    public URLImageBean(PortletRequest req, String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
    }

    /**
     *
     * @param req
     * @param beanId
     * @param url Url of the picture
     * @param alt alt description of the picture
     * @param title title of the picture
     */
    public URLImageBean(PortletRequest req, String beanId, String url, String alt, String title) {
        super(NAME);
        this.beanId = beanId;
        this.request = req;
        this.alt = alt;
        this.title = title;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "<img src=\""+this.url+"\" alt=\""+alt+"\" title=\""+this.title+"\"/>";
    }


}
 


