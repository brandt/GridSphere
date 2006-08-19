package org.gridsphere.services.core.mail;

import javax.mail.MessagingException;

/**
 * @deprecated
 */
public interface MailService {

    public static String MAIL_SERVER_HOST = "gridsphere.mailserver.host";
    public static String MAIL_SENDER = "gridsphere.mail.sender";

    String getMailServiceHost();

    void setMailServiceHost(String mailServiceHost);

    void sendMail(MailMessage msg) throws MessagingException;
}
