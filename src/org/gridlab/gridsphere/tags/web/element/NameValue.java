/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public interface NameValue extends Element {

    public void setValue(String value);

    public String getValue();

    public void setName(String name);

    public String getName();

    public boolean isDisabled();

    public void setDisabled(boolean flag);
}
