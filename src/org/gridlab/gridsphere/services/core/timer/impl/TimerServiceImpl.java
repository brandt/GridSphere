/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.core.timer.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.services.core.timer.TimerService;

/**
 * Timer Service implementation based on the java.util.Timer API.
 */
public class TimerServiceImpl implements PortletServiceProvider, TimerService {

    Map timerMap;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        timerMap = new HashMap();

    }

    public void destroy() {
        Iterator timerIter = timerMap.values().iterator();
        while (timerIter.hasNext()) {
            Timer timer = (Timer) timerIter.next();
            timer.cancel();
        }

    }

    public synchronized void schedule(String timerId, TimerTask task, long delay) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer == null) {
            timer = new Timer();
            timerMap.put(timerId, timer);
        }
        timer.schedule(task, delay);
    }

    public synchronized void schedule(String timerId, TimerTask task, long delay, long period) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer == null) {
            timer = new Timer();
            timerMap.put(timerId, timer);
        }
        timer.schedule(task, delay, period);
    }

    public synchronized void cancel(String timerId) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer != null) {
            timer.cancel();
            timerMap.remove(timerId);
        }
    }

    public synchronized void schedule(String timerId, TimerTask task, Date time) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer == null) {
            timer = new Timer();
            timerMap.put(timerId, timer);
        }
        timer.schedule(task, time);

    }

    public synchronized void schedule(String timerId, TimerTask task, Date firstTime, long period) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer == null) {
            timer = new Timer();
            timerMap.put(timerId, timer);
        }
        timer.schedule(task, firstTime, period);

    }
    public synchronized void scheduleAtFixedRate(String timerId, TimerTask task, long delay, long period) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer == null) {
            timer = new Timer();
            timerMap.put(timerId, timer);
        }
        timer.scheduleAtFixedRate(task, delay, period);

    }

    public synchronized void scheduleAtFixedRate(String timerId, TimerTask task, Date firstTime, long period) {
        Timer timer = (Timer) timerMap.get(timerId);
        if (timer == null) {
            timer = new Timer();
            timerMap.put(timerId, timer);
        }
        timer.scheduleAtFixedRate(task, firstTime, period);
    }

}
