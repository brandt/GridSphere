/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.impl;

public class Cacheable {

    private boolean shared;
    private long seconds;

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean getShared() {
        return shared;
    }

    public void setExpiration(long seconds) {
        this.seconds = seconds;
    }

    public long getExpiration() {
        return seconds;
    }
}
