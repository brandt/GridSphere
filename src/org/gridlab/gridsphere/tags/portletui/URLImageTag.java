/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.ImageBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
/**
 *
 */
public class URLImageTag extends BaseComponentTag {
    private static PortletLog log = SportletLog.getInstance(URLImageTag.class);

    protected ImageBean urlImageBean = null;
    protected String url = new String();
    protected String title = new String();
    protected String alt = new String();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            urlImageBean = (ImageBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (urlImageBean == null) {
                urlImageBean = new ImageBean();
                urlImageBean.setAlt(alt);
                urlImageBean.setTitle(title);
                urlImageBean.setSrc(url);
            }
        } else {
            urlImageBean = new ImageBean();
            urlImageBean.setAlt(alt);
            urlImageBean.setTitle(title);
            urlImageBean.setSrc(url);
        }
        try {
            JspWriter out = pageContext.getOut();
            out.print(urlImageBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }

}



