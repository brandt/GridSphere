/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface ReadOnlyBean extends NameValueBean {

    public void setReadonly(boolean flag);

    public boolean isReadonly();
}
