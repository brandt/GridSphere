/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.jsmash;


public class Smssend {

    public static void main(String[] args) {

        SMSInterface sms = new Sms();
        sms.setMessage(" Hello from Java ");

        try {
            sms.setNumber("+491744840547");
        } catch (SmsProviderNotSupportedException e) {
            System.out.println("lala" + e);
        }
        try {
            sms.send();
        } catch (SmsException e) {
            System.out.println("Send Error :" + e);
        }
    }

}

