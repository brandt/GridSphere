package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.event.ActionEvent;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public class TagBeanFactory {

    private static PortletLog log = SportletLog.getInstance(TagBeanFactory.class);
    private static TagBeanFactory instance = new TagBeanFactory();

    private TagBeanFactory() {}

    public static TagBeanFactory getInstance() {
        return instance;
    }

    public static TagBean getTagBean(PortletRequest req, String id) {
        //System.err.println("Retrieving bean: " + id + " " + getBeanKey(req, id));
        TagBean bean = (TagBean)req.getSession(true).getAttribute(getBeanKey(req, id));

        if (bean != null) {
            bean.setPortletRequest(req);
            System.err.println("found bean id " + id);
        }
        return bean;
    }

    public static void storeTagBean(PortletRequest req, TagBean tagBean) {
        String id = tagBean.getBeanId();
        if (!id.equals("")) {
        //System.err.println("Storing bean: " + id + " " + getBeanKey(req, id));
        req.getSession(true).setAttribute(getBeanKey(req, id), tagBean);
        } else {
            //System.err.println("trying to store bean without id to session");
        }
    }

    public static TagBean createTagBean(Class tagBean, PortletRequest req, String id) {
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
        String compId = req.getParameter(SportletProperties.COMPONENT_ID);
        if (compId == null) {
              compId = (String)req.getAttribute(SportletProperties.COMPONENT_ID);
        }
        log.debug("in getBeankKey: " + id + "_" + compId);
        return id + "_" + compId;
    }

}
