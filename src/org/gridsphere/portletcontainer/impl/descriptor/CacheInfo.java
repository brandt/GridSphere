/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: CacheInfo.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portletcontainer.impl.descriptor;

public class CacheInfo {

    // default is to not cache
    private long expires = 0;
    private boolean shared = false;

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public boolean getShared() {
        return shared;
    }
}
