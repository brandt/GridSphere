/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @version $Id$
*/
package org.gridlab.gridsphere.provider.portletui.tags.gs;

import org.gridlab.gridsphere.provider.portletui.beans.FileInputBean;
import org.gridlab.gridsphere.provider.portletui.tags.gs.BaseComponentTagImpl;
import org.gridlab.gridsphere.provider.portletui.tags.FileInputTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * A <code>FileInputTag</code> represnts a file input element to be used for uploading files
 */
public class FileInputTagImpl extends BaseComponentTagImpl implements FileInputTag {

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
            fileInputBean = (FileInputBean) pageContext.getAttribute(getBeanKey(), PageContext.REQUEST_SCOPE);
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
