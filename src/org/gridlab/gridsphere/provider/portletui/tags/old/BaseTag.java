/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.old;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.provider.ui.beans.TagBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class BaseTag extends TagSupport {

    protected transient static PortletLog log = SportletLog.getInstance(BaseTag.class);

    protected String type;
    protected String value = "";
    protected String name = "name";
    protected boolean isChecked = false;
    protected boolean isDisabled = false;
    protected boolean isReadonly = false;
    protected int size = 20;
    protected int maxLength = 20;
    protected TagBean htmlelement = null;
    protected String bean = new String();
    protected int rows = 10;
    protected int cols = 40;

    public static final String TEXT = "text";
    public static final String PASSWORD = "password";
    public static final String CHECKBOX = "checkbox";
    public static final String RADIO = "radio";
    public static final String SUBMIT = "submit";
    public static final String RESET = "reset";
    public static final String FILE = "file";
    public static final String BUTTON = "button";



    /**
     * Possible attributes of HTML 4.0 <input> tag
     *
     * TYPE=[ text | password | checkbox | radio | submit | reset | file | hidden | image | button ] (type of input)
     * NAME=CDATA (key in submitted form)
     * VALUE=CDATA (value of input)
     * CHECKED (check radio button or checkbox)
     * SIZE=CDATA (suggested number of characters for text input)
     * MAXLENGTH=Number (maximum number of characters for text input)
     * SRC=URI (source for image)
     * ALT=CDATA (alternate text for image input)
     * USEMAP=URI (client-side image map)
     * ALIGN=[ top | middle | bottom | left | right ] (alignment of image input)
     * DISABLED (disable beans)
     * READONLY (prevent changes)
     * ACCEPT=ContentTypes (media types for file upload)
     * ACCESSKEY=Character (shortcut key)
     * TABINDEX=Number (position in tabbing order)
     * ONFOCUS=Script (beans received focus)
     * ONBLUR=Script (beans lost focus)
     * ONSELECT=Script (beans text selected)
     * ONCHANGE=Script (beans value changed)
     */

    public String getBean() {
        return bean;
    }

    public void setBean(String bean) {
        this.bean = bean;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public boolean getDisabled() {
        return isDisabled;
    }

    public void setReadonly(boolean isReadonly) {
        this.isReadonly = isReadonly;
    }

    public boolean getReadonly() {
        return isReadonly;
    }

    public void setMaxlength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMaxlength() {
        return maxLength;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int doStartTag() throws JspException {
        log.debug("------------ START ");
        if (!bean.equals("")) {
            Object beanElement = pageContext.getRequest().getAttribute(SportletProperties.PORTLETID+":"+pageContext.getRequest().getAttribute(SportletProperties.PORTLETID)+":"+bean);
            log.debug("GET: "+SportletProperties.PORTLETID+":"+pageContext.getRequest().getAttribute(SportletProperties.PORTLETID)+":"+bean);;
            try {
                this.htmlelement = (TagBean) beanElement;
            } catch (Exception e) {
                log.debug("This is an error retrieving tag bean with name: " + SportletProperties.PORTLETID+":"+pageContext.getRequest().getAttribute(SportletProperties.PORTLETID)+bean);
                if (beanElement == null) {
                    log.debug("Tag bean attribute with given name is not set!");
                } else {
                    log.debug("Tag bean attribute has invalid type: "
                                       + beanElement.getClass().getName());
                }
                System.err.println(e.getMessage());
           }
        }

        if (this.htmlelement == null) {
            log.debug("Html bean is null!");
        } else {
            try {
                JspWriter out = pageContext.getOut();
                // print out the beantag
                //htmlelement.setCID(pageContext.getRequest().getAttribute(SportletProperties.COMPONENT_ID).toString());
                out.print(htmlelement.toString());
                log.debug("Jup we got content :"+htmlelement.toString()+" for "+SportletProperties.PORTLETID+":"+pageContext.getRequest().getAttribute(SportletProperties.PORTLETID)+":"+bean);
            } catch (Exception e) {
                System.err.println("Error printing tag bean");
                throw new JspTagException(e.getMessage());
            }
        }
         log.debug("------------ END");
        return EVAL_BODY_INCLUDE;
    }


}
