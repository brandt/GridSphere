/**
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.cache.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.cache.CacheService;
import org.gridlab.gridsphere.services.core.timer.TimerService;

/**
 * Simple CacheService implementation based on TimerService
 */
public class CacheServiceImpl implements PortletServiceProvider, CacheService {

    private TimerService timerService = null;
    private Map key2object = null;    
    private boolean isCachingOn = true;

    /**
     * Erases the timed out objects from the cache.
     */
    private static class CacheServiceCleaner extends TimerTask {

        CacheServiceImpl impl;
        public void run() {
            impl.clearExpiredEntries();
        }

        /**
         * @param impl
         */
        public CacheServiceCleaner(CacheServiceImpl impl) {
            this.impl = impl;
        }

    }

    private static class CacheObject {
        public String key;
        public boolean rolling;
        public long expiration;
        public long delay;
        public Object cached;

        public CacheObject(String key, Object cached, long delay, boolean rolling) {
            this.key = key;
            this.cached = cached;
            this.delay = delay;
            this.expiration = System.currentTimeMillis() + delay;
        }

        public void rollExpiration() {
            if (rolling) {
                this.expiration = System.currentTimeMillis() + delay;
            }
        }
    }

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        String isCachingOnStr = config.getInitParameter("isCachingOn");
        if (!isCachingOnStr.equals("true") ||
                !isCachingOnStr.equals("t") ||
                !isCachingOnStr.equals("yes") ||
                !isCachingOnStr.equals("y")) {
            isCachingOn = false;
        }
        try {
            timerService = (TimerService) factory.createPortletService(TimerService.class, config.getServletContext(), true);
            timerService.schedule("org.gridlab.gridslide.service.CacheService", new CacheServiceCleaner(this),1000,60000);
        } catch (PortletServiceUnavailableException e) {
            throw new PortletServiceUnavailableException("Cannot initialize CacheManager service", e);
        } catch (PortletServiceNotFoundException e) {
            throw new PortletServiceUnavailableException("Cannot initialize CacheManager service", e);
        }
        key2object = new HashMap();
    }

    public void destroy() {
        key2object.clear();
        timerService.cancel("org.gridlab.gridslide.service.CacheService");
    }

    public synchronized void cache(String key, Object object, long timeout, boolean rolling) {
        if (isCachingOn)
            key2object.put(key.intern(), new CacheObject(key.intern(), object, timeout, rolling));
    }

    public synchronized void cache(String key, Object object, Date timeout) {
        if (isCachingOn)
            key2object.put(key.intern(), new CacheObject(key.intern(), object, timeout.getTime(), false));
    }

    public synchronized Object getCached(String key) {
        if (isCachingOn) {
            CacheObject cobj = (CacheObject) key2object.get(key.intern());
            if (cobj != null) {
                cobj.rollExpiration();
                return cobj.cached;
            }
        }
        return null;
    }

    protected synchronized void clearExpiredEntries() {
        Set expiredKeys = new HashSet();
        Iterator cacheIter = key2object.values().iterator();
        long currentTime = System.currentTimeMillis();
        while (cacheIter.hasNext()) {
            CacheObject cobj = (CacheObject) cacheIter.next();
            if ( cobj.delay > 0 && cobj.expiration < currentTime) {
                expiredKeys.add(cobj.key);
            }
        }
        Iterator keyIter = expiredKeys.iterator();
        while (keyIter.hasNext()) {
            key2object.remove(keyIter.next());
        }
    }

    public synchronized void removeCached(String key) {
        if (isCachingOn)
            key2object.remove(key.intern());
    }

}
