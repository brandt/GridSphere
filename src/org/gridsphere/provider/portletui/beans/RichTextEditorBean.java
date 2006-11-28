package org.gridsphere.provider.portletui.beans;

import org.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridsphere.services.core.portal.PortalConfigService;

import javax.portlet.RenderResponse;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class RichTextEditorBean extends BaseComponentBean implements TagBean {

    public static final String NAME = "rt";

    private int cols = 0;
    private int rows = 0;
    private String value = null;
    private String action = null;
    public RenderResponse renderResponse;

    /**
     * Constructs a default text area bean
     */
    public RichTextEditorBean() {
        super(RichTextEditorBean.NAME);
    }

    /**
     * Constructs a text area bean from a supplied portlet request and bean identifier
     *
     * @param beanId the bean identifier
     */
    public RichTextEditorBean(String beanId) {
        super(TextEditorBean.NAME);
        this.beanId = beanId;
    }


    public RenderResponse getRenderResponse() {
        return renderResponse;
    }

    public void setRenderResponse(RenderResponse renderResponse) {
        this.renderResponse = renderResponse;
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
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/tiny_mce/tiny_mce.js");
        renderResponse.addProperty("JAVASCRIPT_SRC", contextPath + "/javascript/tiny_mce/richtext.js");
        StringBuffer sb = new StringBuffer();
        String sname = createTagName(name);
        sb.append("<textarea id=\"gridsphere-richtext\" name=\"" + sname + "\" cols=\"" + cols + "\" rows=\"" + rows + "\">");
        return sb.toString();
    }

    public String toEndString() {
        StringBuffer sb = new StringBuffer();
        String result = (value != null) ? value : "";
        sb.append(result + "</textarea>");
        return sb.toString();
    }
}
