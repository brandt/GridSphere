/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.portletui.model;

import org.gridlab.gridsphere.provider.portletui.beans.TagBean;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class DefaultListModel {

    protected List list = new ArrayList();

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
