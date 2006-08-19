/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Cacheable.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl;

public class Cacheable {

    private boolean shared;
    private long seconds;

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean getShared() {
        return shared;
    }

    public void setExpiration(int seconds) {
        this.seconds = seconds;
    }

    public long getExpiration() {
        return seconds;
    }
}
