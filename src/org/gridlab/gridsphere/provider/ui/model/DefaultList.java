/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.model;

import org.gridlab.gridsphere.provider.ui.beans.TagBean;

import java.util.Enumeration;
import java.util.Iterator;

public class DefaultList extends AbstractList implements DefaultListModel {



    public void add(int index, TagBean bean) {
        list.add(index, bean);
    }

    public void addBean(TagBean bean) {
        list.add(bean);
    }

    public boolean isEmpty() {
        if (list.size()==0) {
            return true;
        } else {
            return false;
        }
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void set(int index, TagBean bean) {
        list.set(index, bean);
    }

    public Iterator iterator() {
        return list.iterator();
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }
}
