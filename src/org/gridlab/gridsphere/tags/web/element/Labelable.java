/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface Labelable extends TagBean {

   /**
     * Sets a label to the bean. Will create a label for the bean if it does not exist. The name under this
     * can be used later in the jsp is 'name'-label whereas 'name' is the name of the element.
     * @param label labeltext
     */
    public void setLabel(String label);

    /**
     * Sets the color of the label. If no label exists it creates one.
     * @param color color of the label
     */
    public void setLabelColor(String color) ;

    /**
     * Sets the label.
     * @param label of the element
     */
    public void setLabel(Label label);

    /**
     * Returns the label of the element.
     * @return label of the element
     */
    public Label getLabel();


}
