/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TabbedPaneTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.impl.StoredPortletResponseImpl;
import org.gridsphere.provider.portletui.beans.TabBean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.portlet.RenderResponse;
import javax.portlet.PortletURL;
import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;

/**
 * A <code>TabbedPaneTag</code> represents a tabbed pane</code>
 */
public class TabbedPaneTag extends BaseComponentTag {


    protected String TAB_LABEL_PARAM = "ui.tab.label";
    protected String currentPage = "";
    protected String currentTabLabel = "";

    protected List tabBeans = new ArrayList();

    public void addTabBean(TabBean tabBean) {
        tabBeans.add(tabBean);
    }
    public void setCurrentTab(String currentTab) {
        this.currentTabLabel = currentTab;
    }

    public String getCurrentTab() {
        return currentTabLabel;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public int doStartTag() throws JspException {
        super.doStartTag();
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        if (tabBeans.isEmpty()) return EVAL_PAGE;

        try {
            currentTabLabel = pageContext.getRequest().getParameter(TAB_LABEL_PARAM);
            if (currentTabLabel == null) currentTabLabel = "";
            ServletRequest request = pageContext.getRequest();

            String compVar = (String)request.getAttribute(SportletProperties.COMPONENT_ID_VAR);
            if (compVar == null) compVar = SportletProperties.COMPONENT_ID;
            String cid = (String) request.getAttribute(compVar);
            String pid = (String) request.getAttribute(SportletProperties.PORTLETID);

            JspWriter out = pageContext.getOut();
            out.println("<ul class=\"basictab\">");
            // if this tab is not set, then use this tab (the first tab in the sequence)
            if (currentTabLabel.equals("")) {
                currentTabLabel = ((TabBean)tabBeans.get(0)).getLabel();
            }

            //PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
            RenderResponse res = (RenderResponse)pageContext.getAttribute("renderResponse");

            // print out all tabs
            for (int i = 0; i < tabBeans.size(); i++) {
                TabBean tabBean = (TabBean) tabBeans.get(i);

                //PortletURI uri = res.createURI();
                //uri.addParameter(TAB_LABEL_PARAM, tabBean.getLabel());
                //String href = uri.toString();
                PortletURL url = res.createRenderURL();
                url.setParameter(TAB_LABEL_PARAM, tabBean.getLabel());
                String href = url.toString();


                if (tabBean.getLabel().equals(currentTabLabel)) {
                    currentPage = tabBean.getPage();
                    out.println("<li class=\"selected\">");
                } else {
                    out.println("<li>");
                }
                out.println("<a href=\"" + href + "\">" + tabBean.getTitle() + "</a>");
                out.println("</li>");
            }

            out.println("</ul>");


            System.err.println("jsp page=" + currentPage);

            /*
            Map renderParams = (Map)request.getAttribute(SportletProperties.RENDER_PARAM_PREFIX + pid + "_" + cid);
            if (renderParams == null) renderParams = new HashMap();
            renderParams.put(TAB_LABEL_PARAM, currentTabLabel);
            request.setAttribute(SportletProperties.RENDER_PARAM_PREFIX + pid + "_" + cid, renderParams);
            */

            StringWriter writer = new StringWriter();
            ServletResponse sres = pageContext.getResponse();
            if (res instanceof HttpServletResponse) {
                HttpServletResponse hres = (HttpServletResponse)sres;
                StoredPortletResponseImpl resWrapper = new StoredPortletResponseImpl(hres, writer);
                pageContext.getServletContext().getRequestDispatcher(currentPage).include(request, resWrapper);
                out.println(writer.getBuffer());
            }
            tabBeans.clear();
            request.removeAttribute(SportletProperties.RENDER_PARAM_PREFIX + pid + "_" + cid);
        } catch (Exception e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
}
