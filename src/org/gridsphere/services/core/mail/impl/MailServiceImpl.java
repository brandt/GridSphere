/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: MailServiceImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.services.core.mail.impl;

import org.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridsphere.services.core.mail.MailMessage;
import org.gridsphere.services.core.mail.MailService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * <code>MailServiceImpl</code> is a mail utility used to send {@link org.gridsphere.services.core.mail.MailMessage}s.
 * @deprecated
 */
public class MailServiceImpl implements PortletServiceProvider, MailService {

    private String mailServiceHost = null;

    public void init(PortletServiceConfig config) {

    }

    public void destroy() {
    }

    public String getMailServiceHost() {
        return mailServiceHost;
    }

    public void setMailServiceHost(String mailServiceHost) {
        this.mailServiceHost = mailServiceHost;
    }

    /**
     * Send an email regarding job completion
     *
     * @param msg the MailMessage containing e-mail parameters
     * @throws MessagingException if a an error occurs sending the message
     */
    public void sendMail(MailMessage msg) throws MessagingException {
        Properties props = System.getProperties();
        if (mailServiceHost != null) props.put("mail.smtp.host", mailServiceHost);
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        Message mimeMessage = new MimeMessage(session);
        InternetAddress from = new InternetAddress(msg.getSender());
        InternetAddress to[] = InternetAddress.parse(msg.getEmailAddress());
        mimeMessage.setFrom(from);
        mimeMessage.setRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject(msg.getSubject());
        mimeMessage.setSentDate(new Date());
        mimeMessage.setText(msg.getBody());
        Transport.send(mimeMessage);
    }
}
