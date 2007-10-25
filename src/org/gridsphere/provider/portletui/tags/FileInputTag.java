/*
* @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
* @version $Id$
*/
package org.gridsphere.provider.portletui.tags;

import org.gridsphere.provider.portletui.beans.FileInputBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * A <code>FileInputTag</code> represnts a file input element to be used for uploading files
 */
public class FileInputTag extends BaseComponentTag {

    protected FileInputBean fileInputBean = null;
    protected int size = 0;
    protected int maxlength = 0;

    /**
     * Returns the (html) size of the field
     *
     * @return size of the field
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the (html) size of the field
     *
     * @param size size of the field
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the (html) maxlength of the field
     *
     * @return the max length of the field
     */
    public int getMaxlength() {
        return maxlength;
    }

    /**
     * Sets the (html) max length of the field
     *
     * @param maxlength the max length of the field
     */
    public void setMaxlength(int maxlength) {
        this.maxlength = maxlength;
    }

    public int doStartTag() throws JspException {
        if (!beanId.equals("")) {
            fileInputBean = (FileInputBean) getTagBean();
            if (fileInputBean == null) {
                fileInputBean = new FileInputBean();
                fileInputBean.setBeanId(beanId);
                this.setBaseComponentBean(fileInputBean);
            } else {
                this.updateBaseComponentBean(fileInputBean);
            }
        } else {
            fileInputBean = new FileInputBean();
            this.setBaseComponentBean(fileInputBean);
            if (maxlength != 0) fileInputBean.setMaxLength(maxlength);
            if (size != 0) fileInputBean.setSize(size);
        }

        //debug();

        try {
            JspWriter out = pageContext.getOut();
            out.print(fileInputBean.toStartString());
        } catch (Exception e) {
            throw new JspException(e.getMessage());
        }

        return SKIP_BODY;
    }


}
