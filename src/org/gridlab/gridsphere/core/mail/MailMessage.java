/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.core.mail;

public class MailMessage {

    public String subject = "";
    public String body = "";
    public String emailAddress = "";
    public String sender = "";

    /**
     * Constructor builds empty mail message
     */
    public MailMessage() {
    }

    /**
     * Constructor builds a mail message
     *
     * @param subject the subject of the email
     * @param body the body of the email
     * @param emailAddress the recipient to mail job info to
     * @param sender the reply-to of the mail
     */
    public MailMessage(String subject, String body, String emailAddress, String sender) {
        this.subject = subject;
        this.body = body;
        this.emailAddress = emailAddress;
        this.sender = sender;
    }

    /**
     * Sets the email subject
     *
     * @param subject the email subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Returns the email subject
     *
     * @return the email subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the email body
     *
     * @param subject the email body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Returns the email body
     *
     * @return the email boody
     */
    public String getBody() {
        return body;
    }

    /**
     * Sets the email address
     *
     * @param subject the email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Returns the email address
     *
     * @return the email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Sets the email sender
     *
     * @param subject the email sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Returns the email sender
     *
     * @return the email sender
     */
    public String getSender() {
        return sender;
    }

}
