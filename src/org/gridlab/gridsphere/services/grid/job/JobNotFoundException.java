/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.grid.job;

public class JobNotFoundException extends Exception {

    public JobNotFoundException() {
        super();
    }

    public JobNotFoundException(String msg) {
        super(msg);
    }
}
