/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class TextBean extends BaseBean implements TagBean {

    protected String text = "";
    protected String key = null;

    public TextBean() {
        super();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        if (request != null) {
            System.err.println("saving into session");
            store(this);
        }
    }

    /**
     * Gets the label of the bean.
     * @return label of the bean
     */
    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
        if (request != null) {
            System.err.println("saving into session");
            store(this);
        }
    }

    public String toString() {
        return text;
    }
}
