package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class ParamBean extends BaseBean {

    private String name = "";
    private String value = "";

    public ParamBean() {}

    public ParamBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
