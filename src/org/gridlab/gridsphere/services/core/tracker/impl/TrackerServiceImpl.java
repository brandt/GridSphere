package org.gridlab.gridsphere.services.core.tracker.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.tracker.TrackerService;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Calendar;
import java.util.List;

public class TrackerServiceImpl extends HibernateDaoSupport implements TrackerService {

    private PortletLog log = SportletLog.getInstance(TrackerServiceImpl.class);

    /**
     * Persists a tracking label to the GridSphere database
     *
     * @param label a label used in identifying the action invoked
     * @param userAgent the user agent string provided by the web browser
     * @param userName the user name
     */
    public void trackURL(String label, String userAgent, String userName) {
        TrackerInfo info = new TrackerInfo();
        info.setLabel(label);
        info.setDate(Calendar.getInstance().getTime().getTime());
        info.setUserAgent(userAgent);
        info.setUserName(userName);

        this.getHibernateTemplate().saveOrUpdate(info);
    }

    public List getTrackingActions() {
        return this.getHibernateTemplate().find("from " + TrackerAction.class.getName() + " as trackeraction");
    }

    public TrackerAction getTrackingAction(String actionName) {
        List actions = this.getHibernateTemplate().find("from " + TrackerAction.class.getName() + " as trackeraction where trackeraction.Action=\'" + actionName + "'");
        if ((actions != null) && (!actions.isEmpty())) {
            return (TrackerAction)actions.get(0);
        }
        return null;
    }

    public void addTrackingAction(TrackerAction action) {
        this.getHibernateTemplate().saveOrUpdate(action);
    }

    public void removeTrackingAction(String action) {
        this.getHibernateTemplate().delete(action);
    }

    public void clearTrackingActions() {
        this.getHibernateTemplate().delete("from " + TrackerAction.class.getName() + " as trackeraction ");
    }

    /**
     * Return a list of the available labels
     *
     * @return a list of the available labels
     */
    public List getTrackingLabels() {
        return this.getHibernateTemplate().find("select tracker.Label from " +  TrackerInfo.class.getName() + " as tracker");
    }

    /**
     * Return a list of TrackerInfo objects for the provided label
     *
     * @return a list of TrackerInfo objects for the provided label
     */
    public List getTrackingInfoByLabel(String label) {
        return this.getHibernateTemplate().find("from " + TrackerInfo.class.getName() + " as tracker where tracker.Label='" + label + "'");
    }


    public boolean hasTrackingAction(String actionName) {
        TrackerAction ta = getTrackingAction(actionName);
        return ((ta != null) && (ta.isEnabled()));
    }
}
