/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

import java.util.*;

public class ListBoxModel extends MultipleSelectListModel {


    private int listboxsize = 1;

    public int getListboxsize() {
        return listboxsize;
    }

    public void setListboxsize(int listboxsize) {
        this.listboxsize = listboxsize;
    }

    /**
     * if the model allows multiple selection it will return true otherwise false
     * @return true/false for multiple seletion
     */
    public boolean isMultipleSelection() {
        return this.multipleselection;
    }

    /**
     * allows multiple selection in the listbox
     * @param flag true/false to allow multiple selection
     */
    public void setMultipleSelection(boolean flag) {
        this.multipleselection = flag;
    }


}
