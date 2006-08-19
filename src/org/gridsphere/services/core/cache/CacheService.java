/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: CacheService.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.cache;



/**
 * Simple Cache service. Caches java objects with a String key and a timeout.
 */
public interface CacheService {

    public static final String NO_CACHE = "org.gridsphere.services.core.cache.CacheService.NO_CACHE";

    public void cache(String key, Object object, long timeout);

    public void removeCached(String key);

    public Object getCached(String key);
}
