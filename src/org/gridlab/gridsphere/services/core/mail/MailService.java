package org.gridlab.gridsphere.services.core.mail;

import javax.mail.MessagingException;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Jun 18, 2004
 * Time: 9:24:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MailService {

    public static String MAIL_SERVER_HOST = "gridsphere.mailserver.host";
    public static String MAIL_SENDER = "gridsphere.mail.sender";
  
    String getMailServiceHost();

    void setMailServiceHost(String mailServiceHost);

    void sendMail(MailMessage msg) throws MessagingException;
}
