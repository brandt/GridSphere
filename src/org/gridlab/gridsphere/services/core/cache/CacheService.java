/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.cache;

import java.util.Date;

/**
 * Simple Cache service. Caches java objects with a String key and a timeout.
 */
public interface CacheService {

    public static final String NO_CACHE =  "org.gridlab.gridsphere.services.core.cache.CacheService.NO_CACHE";
    void cache(String key, Object object, long timeout, boolean rolling);

    void cache(String key, Object object, Date timeout);

    void removeCached(String key);

    Object getCached(String key);
}
