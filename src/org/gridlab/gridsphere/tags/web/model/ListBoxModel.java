/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

import java.util.*;

public class ListBoxModel extends MultipleSelectListModel {

    private int WIDTH_TRUNCATE = 10;

    // width of the listbox
    private int listboxwidth = 0;
    // size of the listbox on the screen
    private int listboxsize = 1;

    /**
     * Returns the width (in characters) of the listbox
     * @return width of the listbox
     */
    public int getListBoxWidth() {
        return listboxwidth;
    }

    /**
     * Sets the width of the listbox (in characters).  If length
     * is > 10 then the label gets truncated like 'verylongteststring' will be 'verylongtest...'
     * if width is 15
     * @param listboxwidth width of the listbox
     */
    public void setListBoxWidth(int listboxwidth) {
        this.listboxwidth = listboxwidth;
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            ListSelectItem lsi = (ListSelectItem)it.next();
            lsi.setLabel(fitToWidth(lsi.getLabel(), listboxwidth));
        }
    }

    /**
     * Returns the size of the listbox
     * @return the size
     */
    public int getListBoxSize() {
        return listboxsize;
    }

    /**
     * sets the listboxsize, if the size is set to 1, multipleSelection will be false
     * @param listboxsize size of the listbox
     */
    public void setListBoxSize(int listboxsize) {
        this.listboxsize = listboxsize;
        if (listboxsize==1) {
            this.setMultipleSelection(false);
        }
    }

    /**
     * if the model allows multiple selection it will return true otherwise false
     * @return true/false for multiple seletion
     */
    public boolean isMultipleSelection() {
        return this.multipleselection;
    }

    /**
     * allows multiple selection in the listbox, not possible if listboxsize = 1
     * @param flag true/false to allow multiple selection
     */
    public void setMultipleSelection(boolean flag) {
        this.multipleselection = flag;
        if (listboxsize==1) { this.multipleselection = false; }
    }


    private String fitToWidth(String text, int width) {

        String result = text;

        if (width>5) {
            int length = text.length();

            if (length>width && length>WIDTH_TRUNCATE) {
                result = text.substring(0,width-3);
                result = result+"...";
            }

            if (length<width) {
                result = text;
                for (int i=length;i<width;i++) {
                    result = result+"&nbsp;";
                }
            }
        }
        return result;
    }

    /**
     * Adds a listboxitem to the list, will not other then instanceof ListBoxItem
     * @param item a listboxitem
     */
    public void addItem(ListSelectItem item) {
        if (item instanceof ListSelectItem) {
            item.setLabel(fitToWidth(item.getLabel(), listboxwidth));
            elements.add(item);
        }
    }

    /**
     * Inititaled the model with a list of items
     * @param items list of items
     */
    public void putItems(List items) {
        elements = items;
        setListBoxWidth(listboxwidth);
    }


}
