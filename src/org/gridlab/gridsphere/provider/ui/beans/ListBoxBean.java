/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

public class ListBoxBean extends DropDownListBean implements ListBox {

    public ListBoxBean(String name) {
        super(name);
        this.size = 10;
    }

    /**
     * Sets multiple choice on/off in the listbox.
     * @param flag on/off multiple
     */
    public void setMultiple(boolean flag) {
        this.multiple = flag;
    }

    /**
     * Returns true/false if multiple choice is on/off
     * @return true/false
     */
    public boolean isMultiple() {
        return multiple;
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
        return this.size;
    }


}
