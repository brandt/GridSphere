package org.gridlab.gridsphere.services.core.messaging.impl;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.core.messaging.TextMessagingService;
import org.gridlab.gridsphere.services.core.messaging.MessagingID;
import org.gridsphere.tmf.TextMessagingException;
import org.gridsphere.tmf.TMFService;
import org.gridsphere.tmf.TMFFactory;
import org.gridsphere.tmf.TextMessagingSession;
import org.gridsphere.tmf.message.GenericMessage;
import org.gridsphere.tmf.message.InstantMessage;
import org.gridsphere.tmf.message.MailMessage;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.Set;
import java.util.List;
import java.io.File;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

public class TextMessagingServiceImpl extends HibernateDaoSupport implements ServletContextAware, TextMessagingService {

    private static PortletLog log = SportletLog.getInstance(TextMessagingServiceImpl.class);

    private TMFService tmfService = null;

    private ServletContext servletContext = null;

    public TextMessagingServiceImpl() {}

    public void init() {
        String configfile = servletContext.getRealPath("WEB-INF"+
                File.separator+"CustomPortal"+File.separator+"tmf");
        tmfService = TMFFactory.createTMFService(configfile);
        log.info("Starting up TextMessagingService with config " + configfile);
        tmfService.startup();
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public void destroy() {
        this.shutdown();
    }

    public InstantMessage getInstantMessage() {
        return tmfService.getInstantMessage();
    }

    public MailMessage getMailMessage() {
        return tmfService.getMailMessage();
    }

    public void send(GenericMessage message) throws TextMessagingException {
        tmfService.sendMessage(message);
    }

    public void startup() {
        tmfService.startup();
    }

    public void shutdown() {
        tmfService.shutdown();
    }

    public Set getServices() {
        return tmfService.getServices();
    }

    private MessagingID getMessagingID(String serviceid, String username) {
        MessagingID mid = new MessagingID();
        String oql = "select mid from "+MessagingID.class.getName()+" mid where mid.serviceid='"+serviceid+
                    "' and mid.username='"+username+"'";
        List midList = this.getHibernateTemplate().find(oql);
        if ((midList != null) && (!midList.isEmpty())) {
            mid = (MessagingID)midList.get(0);
        }
        if (mid==null) mid = new MessagingID();
        return mid;
    }

    public String getServiceUserID(String serviceid, String username) {
        MessagingID mid = getMessagingID(serviceid, username);
        return mid.getServiceuserid();
    }

    public void setServiceUserID(String serviceid, String username, String serviceuserid) {
        MessagingID mid = getMessagingID(serviceid, username);
        mid.setUsername(username);
        mid.setServiceid(serviceid);
        mid.setServiceuserid(serviceuserid);
        this.getHibernateTemplate().saveOrUpdate(mid);
    }

    public void addCommands(Set commands) {
        tmfService.addCommands(commands);
    }

    public TextMessagingSession getSession(String serviceid,String userid) {
        return tmfService.getTextMessagingSession(serviceid, userid);
    }
}
