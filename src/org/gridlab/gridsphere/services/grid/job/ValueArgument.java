/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

public class ValueArgument extends Argument {

    public ValueArgument() {
        super();
        setValue("");
    }

    public ValueArgument(String value) {
        super();
        setValue(value);
    }
}
