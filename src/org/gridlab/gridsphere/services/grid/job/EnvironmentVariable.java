/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class EnvironmentVariable {

    private String name = "";
    private String value = "";

    public EnvironmentVariable() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        if (value == null) {
            this.name = "";
        } else {
            this.name = value.trim();
        }
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        if (value == null) {
            this.value = "";
        } else {
            this.value = value.trim();
        }
    }
}
