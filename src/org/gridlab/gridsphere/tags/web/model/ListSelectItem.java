/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

public class ListSelectItem {

    private String label = new String();
    private String value = new String();
    private boolean selected = false;


    public ListSelectItem() {
        super();
    }

    public ListSelectItem(String label, String value) {
        this.value = value;
        this.label = label;
    }

    public ListSelectItem(String label, String value, boolean selected) {
        this.value = value;
        this.label = label;
        this.selected = selected;
    }


    /**
     * gets the label of the item
     * @return the label
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * sets the label of the item
     * @param label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * gets the value of the item
     * @return value of the item
     */
    public String getValue() {
        return this.value;
    }

    /**
     * sets the value of the item
     * @param value of the item
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Returns true if the item is selected
     * @return true if item is selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets if the item is selected
     * @param flag flag which un/selects the item
     */
    public void setSelected(boolean flag) {
        this.selected = flag;
    }

}
