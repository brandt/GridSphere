/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.URLImageBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
/**
 *
 */
public class URLImageTag extends BaseComponentTag {
    private static PortletLog log = SportletLog.getInstance(URLImageTag.class);

    protected URLImageBean urlImageBean = null;
    protected String url = new String();
    protected String title = new String();
    protected String alt = new String();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        System.out.println("============== URL WAS SET to"+url);
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        System.out.println("============== TITEL WAS SET TO "+title);
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
           urlImageBean = (URLImageBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
           if (urlImageBean == null) {
               urlImageBean = new URLImageBean();
           } else {
               url = urlImageBean.getUrl();
               alt = urlImageBean.getAlt();
               title = urlImageBean.getTitle();
           }
       } else {
            urlImageBean = new URLImageBean(); // worry about this later owehrens
       }
       return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(urlImageBean);
            //System.err.println("inTextTag: adding " + textBean.toString());
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(urlImageBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }

        return EVAL_BODY_INCLUDE;
    }

}
 


