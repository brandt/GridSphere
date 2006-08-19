/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: DefaultListModel.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.provider.portletui.model;

import org.gridsphere.provider.portletui.beans.TagBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A <code>DefaultListModel</code> is a general tag bean container that stores tag beans in a list
 */
public class DefaultListModel {

    protected List list = new ArrayList();

    /**
     * Adds a tag bean to the list in the supplied location
     *
     * @param index the list location to add the tag bean to
     * @param bean  the tag bean
     */
    public void addBean(int index, TagBean bean) {
        list.add(index, bean);
    }

    /**
     * Adds a tag bean to the list
     *
     * @param bean the tag bean
     */
    public void addBean(TagBean bean) {
        list.add(bean);
    }

    /**
     * Returns true if the list is empty, false otherwise
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a tag bean from the list at a given location
     *
     * @param index the location of the tag bean to remove
     */
    public void remove(int index) {
        list.remove(index);
    }

    /**
     * Returns a list iterator
     *
     * @return a list iterator
     */
    public Iterator iterator() {
        return list.iterator();
    }

    /**
     * Returns the size of the list
     *
     * @return the size of the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Clears the list of all tag beans
     */
    public void clear() {
        list.clear();
    }
}
