/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.model;

import org.gridlab.gridsphere.tags.web.element.TagBean;

import java.util.Enumeration;
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
