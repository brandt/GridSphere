/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public abstract class BaseListBean extends NameBean implements BaseList {

    String name = new String();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected String getTagName() {
        return "gstag:";
    }

    public abstract String toString();
}
