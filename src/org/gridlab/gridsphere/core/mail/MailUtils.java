/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;

public class MailUtils {

    /**
     * Send an email regarding job completion
     *
     * @param msg the MailMessage containing e-mail parameters
     * @param mailServer the mail server
     */
    public static void sendMail(MailMessage msg,
                                String mailServer) throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailServer);
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(true);
        Message mimeMessage = new MimeMessage(session);
        InternetAddress from = new InternetAddress(msg.getSender());
        InternetAddress to[] = InternetAddress.parse(msg.getEmailAddress());
        mimeMessage.setFrom(from);
        mimeMessage.setRecipients(Message.RecipientType.TO,to);
        mimeMessage.setSubject(msg.getSubject());
        mimeMessage.setSentDate(new Date());
        mimeMessage.setText(msg.getBody());
        Transport.send(mimeMessage);
    }


}
