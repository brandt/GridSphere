package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.RichTextEditorBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/*
* @author <a href="mailto:wehrens@gridsphere.org">Oliver Wehrens</a>
* @version $Id$
*/
public class RichTextEditorTag extends ActionTag {

    protected RichTextEditorBean richTextEditorBean = null;
    private int cols = 0;
    private int rows = 0;
    private String value = null;


    /**
     * Returns the number of columns of the text area
     *
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns of the text area
     *
     * @param cols number of cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Return the number of rows of the text area
     *
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the text area
     *
     * @param rows number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            richTextEditorBean = (RichTextEditorBean) getTagBean();
            if (richTextEditorBean == null) {
                richTextEditorBean = new RichTextEditorBean();
                richTextEditorBean.setRows(rows);
                richTextEditorBean.setCols(cols);
                this.setBaseComponentBean(richTextEditorBean);
            } else {
                if ((richTextEditorBean.getCols() == 0) && (cols != 0)) {
                    richTextEditorBean.setCols(cols);
                }
                if ((richTextEditorBean.getRows() == 0) && (rows != 0)) {
                    richTextEditorBean.setRows(rows);
                }
                this.updateBaseComponentBean(richTextEditorBean);
            }
        } else {
            richTextEditorBean = new RichTextEditorBean();
            richTextEditorBean.setRows(rows);
            richTextEditorBean.setCols(cols);
            this.setBaseComponentBean(richTextEditorBean);
        }

        richTextEditorBean.setAction(createActionURI());


        //debug();

        try {
            JspWriter out = pageContext.getOut();
            out.print(richTextEditorBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {

        if ((bodyContent != null) && (value == null)) {
            richTextEditorBean.setValue(bodyContent.getString());
        }

        try {
            JspWriter out = pageContext.getOut();
            out.print(richTextEditorBean.toEndString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return EVAL_PAGE;
    }
}
