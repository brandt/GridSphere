/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

public class TextAreaTag extends BaseComponentTag {

    public static final String TEXTAREA_STYLE = "portlet-frame-textarea";
    protected TextAreaBean textAreaBean = null;
    protected int cols = 0;
    protected int rows = 0;;

    /**
     *  Gets the number of columns of the TextArea.
     * @return number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Sets the number of columns of the TextArea.
     * @param cols number of cols
     */
    public void setCols(int cols) {
        this.cols = cols;
    }

    /**
     * Return the number of rows of the textarea.
     * @return number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Sets the number of rows of the textarea.
     * @param rows number of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    public int doEndTag() throws JspException {
        //textAreaBean.setCssStyle(TEXTAREA_STYLE);
        this.cssStyle = TEXTAREA_STYLE;
        textAreaBean = (TextAreaBean)pageContext.getSession().getAttribute(getBeanKey());
        if (textAreaBean == null) {
            textAreaBean = new TextAreaBean();
            textAreaBean.setRows(rows);
            textAreaBean.setCols(cols);
            this.setBaseComponentBean(textAreaBean);
        }
        if (!beanId.equals("")) {
            //System.err.println("storing bean in the session");
            store(getBeanKey(), textAreaBean);
        }
        //debug();

        Object parentTag = getParent();
        if (parentTag instanceof ContainerTag) {
            ContainerTag containerTag = (ContainerTag)parentTag;
            containerTag.addTagBean(textAreaBean);
        } else {
            try {
                JspWriter out = pageContext.getOut();
                out.print(textAreaBean.toString());
            } catch (Exception e) {
                throw new JspException(e.getMessage());
            }
        }
        return EVAL_BODY_INCLUDE;
    }


}
