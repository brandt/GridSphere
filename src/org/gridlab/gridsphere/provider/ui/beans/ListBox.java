/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.ui.beans;

public interface ListBox extends DropDownList {

    /**
     * Sets multiple choice on/off in the listbox.
     * @param flag on/off multiple
     */
    public void setMultiple(boolean flag);

    /**
     * Returns true/false if multiple choice is on/off
     * @return true/false
     */
    public boolean isMultiple();

    /**
     * Sets the size of the listbox.
     * @param size the size of the listbox
     */
    public void setSize(int size);

    /**
     * Returns the size of the listbox.
     * @return size of the listbox
     */
    public int getSize();

}
