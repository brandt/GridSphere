/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.model;

public interface MutableComboBoxModel extends ComboBoxModel {

    public void addElement (Object obj);
    public void insertElementAt (Object obj, int index);
    public void removeElement (Object obj);
    public void removeElementAt (int index);

}
