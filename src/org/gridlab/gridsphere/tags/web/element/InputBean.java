/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public interface InputBean extends ReadOnlyBean {

    public void setSize(int size);

    public int getSize();

    public void setMaxlength(int length);

    public int getMaxlength();

    public void setInputtype(String type);

    public String getInputtype();
}
