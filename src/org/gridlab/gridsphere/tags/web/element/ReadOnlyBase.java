/**
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tags.web.element;

public abstract class ReadOnlyBase extends BaseNameValue implements ReadOnly {

    protected boolean readonly;

    public ReadOnlyBase() {
        super();
    }

    public ReadOnlyBase(String name, String value, boolean disabled, boolean readonly) {
        super(name, value, disabled);
        this.readonly = readonly;
    }

    public void setReadonly(boolean flag) {
        this.readonly = flag;
    }

    public boolean isReadonly() {
        return readonly;
    }

}
