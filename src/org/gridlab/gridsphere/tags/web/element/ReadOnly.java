/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface ReadOnly extends NameValue {

    public void setReadonly(boolean flag);

    public boolean isReadonly();
}
