package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TagBeanFactory {

    private static PortletLog log = SportletLog.getInstance(TagBeanFactory.class);
    private TagBeanFactory() {}

    /**
     * Gets back the prev. saved bean with the modifications from the userinterface.  Returns null
     * if none object was found.
     * @param id name of the bean
     * @return updated elementbean
     */
    public static TagBean createTagBean(ActionEvent event, String id) {
        PortletRequest req = event.getPortletRequest();
        TagBean bean = (TagBean)req.getSession(true).getAttribute(getBeanKey(req, id));
        if (bean != null) {
            bean.setPortletRequest(req);
            System.err.println("found bean id " + id);
        }
        return bean;
    }

    public static TagBean createNewTagBean(Class tagBean, ActionEvent event, String id) {
        PortletRequest req = event.getPortletRequest();
        TagBean bean = null;
        try {
            bean = (TagBean) Class.forName(tagBean.getName()).newInstance();
            bean.setBeanId(id);
            bean.setPortletRequest(req);
        } catch (Exception e) {
            log.error("Unable to create TagBean: " + tagBean.getName());
        }
        return bean;
    }

    protected static String getBeanKey(PortletRequest req, String id) {
        String compId = req.getParameter(GridSphereProperties.COMPONENT_ID);
        System.err.println("in formeventimpl beankey: " + id + "_" + compId);
        return id + "_" + compId;
    }

}
