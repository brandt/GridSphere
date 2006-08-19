/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: TabbedPaneTag.java 4666 2006-03-27 17:47:56Z novotny $
 */
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.portlet.PortletResponse;
import org.gridsphere.portlet.PortletURI;
import org.gridsphere.provider.portletui.beans.TabBean;
import org.gridsphere.provider.portletui.beans.TabbedPaneBean;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.PrintWriter;
import java.util.List;

/**
 * A <code>TableTag</code> represents a table element and is defined by a <code>DefaultTableModel</code>
 */
public class TabbedPaneTag extends BaseComponentTag {

    protected TabbedPaneBean tabbedPaneBean = null;
    protected String paneId = null;
    protected String width = null;

    protected int currentTab = 0;

    public void setPaneId(String paneId) {
        this.paneId = paneId;
    }

    public String getPaneId() {
        return paneId;
    }

    /**
     * Sets the table width
     *
     * @param width the table width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * Returns the table width
     *
     * @return the table width
     */
    public String getWidth() {
        return width;
    }

    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public void addTab(TabBean tabBean) {
        tabbedPaneBean.addBean(tabBean);
    }

    public void removeTab(TabBean tabBean) {
        tabbedPaneBean.removeBean(tabBean);
    }

    public int doStartTag() throws JspException {
        super.doStartTag();
        System.err.println("panetag in doStartTag");
        if (!beanId.equals("")) {
            tabbedPaneBean = (TabbedPaneBean) getTagBean();
            if (tabbedPaneBean == null) {
                tabbedPaneBean = new TabbedPaneBean();
            } else {
                paneId = tabbedPaneBean.getPaneId();
            }
        } else {
            tabbedPaneBean = new TabbedPaneBean();
            if (width != null) tabbedPaneBean.setWidth(width);
        }

        // sets current tab
        String curTab = pageContext.getRequest().getParameter(paneId);
        if (curTab != null) {
            currentTab = Integer.valueOf(curTab).intValue();
        }
        tabbedPaneBean.setCurrentTab(currentTab);

        try {
            JspWriter out = pageContext.getOut();
            out.print(tabbedPaneBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        System.err.println("panetag in doEndTag");
        // create rest of tab
        try {

            PrintWriter w = pageContext.getResponse().getWriter();

            PortletResponse res = (PortletResponse) pageContext.getAttribute("portletResponse");
            //PortletResponse portletResponse = new StoredPortletResponseImpl((HttpServletResponse)res, res.getOutputStream());
            List tabBeans = tabbedPaneBean.getBeans();
            if (tabBeans.isEmpty()) return EVAL_PAGE;

            // make sure currentTab is active
            for (int i = 0; i < tabBeans.size(); i++) {
                TabBean tabBean = (TabBean) tabBeans.get(i);
                if (currentTab == i) {
                    tabBean.setActive(true);
                } else {
                    tabBean.setActive(false);
                }
            }

            // added the closing slash of tag for XHTML 1.0 Strict compliance
            w.print("<input type=\"hidden\" name=\"" + paneId + " value=\"" + currentTab + "\" />");
            w.print("<table width=\"" + width + "\">");
            // first create tabs
            w.print("<tr>");
            int twidth = 100;
            if (width.endsWith("%")) {
                twidth = Integer.valueOf(width.substring(0, width.length() - 1)).intValue();
            } else {
                twidth = Integer.valueOf(width).intValue();
            }

            int tabwidth = twidth / tabBeans.size();
            for (int i = 0; i < tabBeans.size(); i++) {
                // Attribute 'width' replaced by 'style="width:"' for XHTML 1.0 Strict compliance                                
                w.print("<td style=\"width:" + tabwidth + "%\">");
                TabBean tabBean = (TabBean) tabBeans.get(i);
                String target = paneId + i;
                if (tabBean.getActive()) {
                    w.print("<a name=" + target + ">" + tabBean.getTitle() + "</a>");
                } else {
                    if (tabBean.getVisible()) {
                        PortletURI uri = res.createURI();
                        uri.addParameter(paneId, String.valueOf(i));
                        String title = this.getLocalizedText(tabBean.getTitle());
                        if (title == null) title = tabBean.getTitle();
                        w.print("<a name=" + target + " href=\"" + uri.toString() + "#" + target + "\">" + title + "</a>");
                    } else {
                        w.print("<a name=" + target + ">" + tabBean.getTitle() + "</a>");
                    }
                }
                w.print("</td>");
            }
            w.print("</tr>");

            // second include active tab page
            for (int i = 0; i < tabBeans.size(); i++) {
                TabBean tabBean = (TabBean) tabBeans.get(i);
                if (tabBean.getActive()) {
                    String jspPage = tabBean.getPage();
                    System.err.println("jsp page=" + jspPage);

                    ServletRequest request = pageContext.getRequest();
                    ServletResponse response = pageContext.getResponse();

                    //PrintWriter pwout = portletResponse.getWriter();
                    w.println("<tr><td>");
                    // Very important here... must pass it the appropriate jsp writer!!!
                    // Or else this include won't be contained within the parent content
                    // but either before or after it.
                    //rd.include(request, new ServletResponseWrapperInclude(response, pageContext.getOut()));

                    pageContext.getServletContext().getRequestDispatcher(jspPage).include(request, pageContext.getResponse());


                    w.println("</td></tr>");
                }
            }
            w.print("</table>");
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }
        super.doEndTag();
        return EVAL_PAGE;
    }
}
