/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.ui.model;

import org.gridlab.gridsphere.provider.ui.beans.TagBean;

import java.util.Iterator;

public interface DefaultListModel extends AbstractListModel {

    public void add(int index, TagBean bean);

    public void addBean(TagBean bean);

    public boolean isEmpty();

    public void remove(int index);

    public void set(int index, TagBean bean);

    public Iterator iterator();

    public int size();

}
