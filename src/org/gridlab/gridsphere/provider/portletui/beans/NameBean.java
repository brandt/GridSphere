/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.provider.portletui.beans.BaseBean;

public abstract class NameBean extends BaseBean implements Nameable, Labelable, TagBean  {

    protected String name = new String();
    protected Label label = null;

    public NameBean() {
        super();
    }

    public NameBean(String name) {
        this.name = name;
    }

    /**
     * Sets the name of the bean.
     * @param name the name of the bean
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the bean.
     * @return name of the bean
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets a label to the bean. Will create a label for the bean if it does not exist. The name under this
     * can be used later in the jsp is 'name'-label whereas 'name' is the name of the beans.
     * @param label labeltext
     */
    public void setLabel(String label) {

        if (this.label==null)  {
            this.label = new LabelBean();
            this.label.setName(name+"-label");
        }
        this.label.setValue(label);
    }

    /**
     * Sets the color of the label. If no label exists it creates one.
     * @param color color of the label
     */
    public void setLabelColor(String color) {
        if (this.label==null) {
            this.label = new LabelBean();
        }
        this.label.setColor(color);
    }

    /**
     * Sets the label.
     * @param label label
     */
    public void setLabel(Label label) {
        this.label = label;
    }

    /**
     * Returns the label of the beans.
     * @return label of the beans
     */
    public Label getLabel() {
        return label;
    }

}
