/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.model;
import java.util.Iterator;

public interface ListModel {

    public Object getElementAt(int index);
    public int getSize();

}
