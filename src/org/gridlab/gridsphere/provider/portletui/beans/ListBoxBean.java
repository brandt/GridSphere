/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.beans;

public class ListBoxBean extends DropDownListBean {

    public ListBoxBean(String name) {
        super(name);
        this.name = name;
        this.size = 10;
    }

    /**
     * Sets the size of the listbox.
     * @param size the size of the listbox
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Returns the size of the listbox.
     * @return size of the listbox
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets multiple choice on/off in the listbox.
     * @param isMultiple determines wether multiple items may be selected
     */
    public void setMultiple(boolean isMultiple) {
        this.isMultiple = isMultiple;
    }

    /**
     * Returns true/false if multiple choice is on/off
     * @return true/false
     */
    public boolean isMultiple() {
        return isMultiple;
    }

}
