/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.jsmash;


public class Smssend {

    public static void main(String[] args) {

        if (args.length==2) {

            SMSInterface sms = new Sms();
            sms.setMessage(args[1]);

            try {
                sms.setNumber(args[0]);
            } catch (SmsProviderNotSupportedException e) {
                System.out.println("Can not send a message to "+args[0]+" (Provider not supported)!");
            }
            try {
                sms.send();
            } catch (SmsException e) {
                System.out.println("Send Error :" + e);
            }
        }  else {
            System.out.println("Usage: \nSmssend <number> <message>\n <number> includes the intl. prefix!");
        }
    }

}

