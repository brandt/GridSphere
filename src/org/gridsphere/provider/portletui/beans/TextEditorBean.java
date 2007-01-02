package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.portal.PortalConfigService;

/**
 * The <code>TextEditorBean</code> represents a text editor provided
 * by Cezary Tomczak at http://gosu.pl/dhtml/SimpleTextEditor.html
 */
public class TextEditorBean extends BaseComponentBean implements TagBean {

    private int cols = 0;
    private int rows = 0;
    private String value = null;
    private String action = null;
    private boolean viewsource = true;

    /**
     * Constructs a default text area bean
     */
    public TextEditorBean() {
        super(TagBean.TEXTEDITOR_NAME);
    }

    /**
     * Constructs a text area bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public TextEditorBean(String beanId) {
        super(TagBean.TEXTEDITOR_NAME);
        this.beanId = beanId;
    }

    /**
     * Returns true if text editor should allow users to edit/view HTML source
     *
     * @return true if text editor should allow users to edit/view HTML source
     */
    public boolean getViewsource() {
        return viewsource;
    }

    /**
     * Set to true if text editor should allow users to edit/view HTML source
     *
     * @param viewsource is true if text editor should allow users to edit/view HTML source
     */
    public void setViewsource(boolean viewsource) {
        this.viewsource = viewsource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the number of columns of the TextArea.
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns of the TextArea.
     *
     * @param cols number of cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Return the number of rows of the textarea.
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the textarea.
     *
     * @param rows number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * Sets the bean value
     *
     * @param value the bean value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns the bean value
     *
     * @return the bean value
     */
    public String getValue() {
        return value;
    }


    public String toStartString() {
        PortalConfigService configService = (PortalConfigService) PortletServiceFactory.createPortletService(PortalConfigService.class, true);
        // deal with ROOT context case
        String contextPath = configService.getProperty("gridsphere.deploy");
        if (!contextPath.equals("")) contextPath = "/" + contextPath;
        renderResponse.setProperty("CSS_HREF", contextPath + "/css/SimpleTextEditor.css");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/SimpleTextEditor.js");
        StringBuffer sb = new StringBuffer();
        sb.append("<form action=\"" + action + "\" method=\"post\">");
        String sname = createTagName(name);
        sb.append("<textarea id=\"body\" name=\"" + sname + "\" cols=\"" + cols + "\" rows=\"" + rows + "\">");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        String result = (value != null) ? value : "";
        sb.append(result + "</textarea>");
        sb.append("<script type=\"text/javascript\">");
        String sname = createTagName(name);
        sb.append("var ste = new SimpleTextEditor(\"body\", \"" + sname + "\", " + viewsource + ", \"ste\");");
        sb.append("ste.init();");
        sb.append("</script>");
        sb.append("<input type=\"submit\" value=\"Submit\" onclick=\"ste.submit();\">");
        sb.append("</form>");
        return sb.toString();
    }

}
