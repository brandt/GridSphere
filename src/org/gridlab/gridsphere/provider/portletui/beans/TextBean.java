/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class TextBean extends BaseBean implements TagBean {

    protected String label = new String();

    public TextBean() {
        super();
    }

    public TextBean(String label) {
        super();
        this.label = label;
    }

    /**
     * Gets the label of the bean.
     * @return label of the bean
     */
    public String getText() {
        return label;
    }

    /**
     * Sets the label of the bean
     * @param label the label to be set
     */
    public void setText(String label) {
        this.label = label;
        if (request != null) {
            System.err.println("saving into session");
            store(this);
        }
    }

    public String toString() {
        return label;
    }
}
