/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

import java.util.*;

public abstract class SelectListModel {

    private String name = new String();
    protected List elements = new ArrayList();

    /**
     * returns an iterator on the items
     * @return Iterator on the elements of the list
     */
    public Iterator iterator() {
        return elements.iterator();
    }

    /**
     * Gets the name of the Model
     * @return name of the model
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the model
     * @param name of the model
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * returns all elements of the model as list listboxitems
     * @return list of listboxitems
     */
    public List getItems() {
        return this.elements;
    }

    /**
     * Returns the item at a position
     * @param position  position of the item
     * @return the item at the position, returns null if position is <0 or >number of elements
     */
    public ListSelectItem getItem(int position) {
        if (position>elements.size() || position<0) {
            return null;
        } else {
            return (ListSelectItem)elements.get(position);
        }
    }

    /**
     * gets the first item with the specified value (value should be unique anyway)
     * @param value the value of the listboxitem, return null if no such item exists
     */
    public ListSelectItem getItem(String value) {
        ListSelectItem item = null;
        for (int i=0;i<elements.size()-1;i++) {
            if (((ListSelectItem)(elements.get(i))).getValue().equals(value)) {
                i=elements.size();
                return (ListSelectItem)(elements.get(i));
            }
        }
        return item;
    }

    /**
     * Checks if an item on a positin in the list is selected. Returns false if position is out of range
     * @param position position of the item
     */
    public boolean isSelected(int position) {
        ListSelectItem item = getItem(position);
        if (item!=null) {
            return item.isSelected();
        } else {
            return false;
        }
    }


    /**
     * Checks if the item with a label is selected, return false if such a element does not exist
     * @param value value of the item
     */
    public boolean isSelected(String value) {
        ListSelectItem item = getItem(value);
        if (item!=null) {
            return item.isSelected();
        } else {
            return false;
        }
    }

    /**
     * Adds a listboxitem to the list, will not other then instanceof ListBoxItem
     * @param item a listboxitem
     */
    public void addItem(ListSelectItem item) {
        if (item instanceof ListSelectItem) {
            elements.add(item);
        }
    }

    /**
     * Inititaled the model with a list of items
     * @param items list of items
     */
    public void putItems(List items) {
        elements = items;
    }

    /**
     * Removes an item at a position
     * @param position position of the item
     */
    public void removeItem(int position) {
        if (!(position<0) || !(position>elements.size())) {
            elements.remove(position);
        }
    }

    /**
     * Removes an item with the specified value
     * @param value vaalue of the item
     */
    public void removeItem(String value) {
        Iterator it = elements.iterator();
        while (it.hasNext()) {
            if (((ListSelectItem)it.next()).getValue().equals(value)) {
                it.remove();
            }
        }
    }

    /**
     * Clears up all items
     */
    public void clearItems() {
        elements = new ArrayList();
    }

    /**
     * returns the number of items
     * @return number of items
     */
    public int getListSize() {
        return elements.size();
    }

}
