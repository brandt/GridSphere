/* 
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */
package org.gridlab.jsmash;

public interface SMSInterface {
    /**
     * sets the number where one want to send the message to
     * @param number the phonenumber of the receipent
     */
    void setNumber(String number) throws SmsProviderNotSupportedException;

    /**
     * sets the message one whishes to send
     * @param message the message text
     */
    void setMessage(String message);

    /**
     * sends the message
     *
     * @throws SmsException if number/message is not set or the smash host is not reachable
     */
    void send() throws SmsException;
}
