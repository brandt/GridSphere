/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.portletui;

import org.gridlab.gridsphere.provider.portletui.beans.TextAreaBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

public class TextAreaTag extends BaseComponentTag {

    protected TextAreaBean textAreaBean = null;
    protected int cols = 0;
    protected int rows = 0;

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
        if (!beanId.equals("")) {
            textAreaBean = (TextAreaBean)pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
            if (textAreaBean == null) {
                textAreaBean = new TextAreaBean();
                textAreaBean.setBeanId(beanId);
                textAreaBean.setRows(rows);
                textAreaBean.setCols(cols);
                this.setBaseComponentBean(textAreaBean);
            } else {
                if ((textAreaBean.getCols() == 0) && (cols != 0)) {
                    textAreaBean.setCols(cols);
                }
                if ((textAreaBean.getRows() == 0) && (rows != 0)) {
                    textAreaBean.setRows(rows);
                }
                this.updateBaseComponentBean(textAreaBean);
            }
        } else {
            textAreaBean = new TextAreaBean();
            textAreaBean.setRows(rows);
            textAreaBean.setCols(cols);
            this.setBaseComponentBean(textAreaBean);
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
