/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.jsmash;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;

public class Sms implements SMSInterface {

    private String Number = new String();
    private String Message = new String();

    private String SmsHost = "portal.aei.mpg.de";
    private int SmsPort = 7077;
    private String UserAgent = "GridLab SMS";
    private Hashtable Provider = new Hashtable();
    private String Version = "0.1";

    /**
     * creates a new Sms object
     */
    public Sms() {
        super();
        initProvider();
    }


    /**
     * creates a new SMS
     * @param number number to send the jsmash to
     * @param message textmessage string
     */
    public Sms(String number, String message) throws SmsProviderNotSupportedException {
        super();
        initProvider();
        setNumber(number);
        Message = message;
    }


    public String getVersion() {
        return Version;
    }

    /**
     * sets the number where one want to send the message to
     * @param number the phonenumber of the receipent
     */
    public void setNumber(String number) throws SmsProviderNotSupportedException {

        boolean supportedNumber = false;
        String myPrefix = getProviderNumber(number);

        Enumeration e = Provider.keys();
        while (e.hasMoreElements()) {
            String providernumber = (String) e.nextElement();
            if (myPrefix.equals(providernumber)) {
                supportedNumber = true;
            }
        }

        if (!supportedNumber)
            throw new SmsProviderNotSupportedException("Provider with prefix " + myPrefix + " is not known " +
                    " or number is not valid. Please use also the internal dialprefix in front of the number.");
        Number = number;
    }

    /**
     * sets the message one whishes to send
     * @param message the message text
     */
    public void setMessage(String message) {
        Message = message;
    }

    /**
     * inits the available provider
     */
    private void initProvider() {
        /*
        from http://www.teltarif.de/mobilfunk/
        T-Mobil:  0151, 0160, 017-0/1/5
        Vodafone:  0152, 0162, 017-2/3/4
        E-Plus:  0163, 0177, 0178
        o2 Germany:  0176, 0179
        Quam:  01505

        make sure your smash.cfg has these entries; should be in a properties file

        put the +49 before to check against other providers in other
        countries using the same prefix
        */

        Provider.put("+49151", new ProviderConfig("T-Mobile", "0151", "tmobile-de"));
        Provider.put("+49152", new ProviderConfig("Vodafone", "0152", "vodafone-de"));
        Provider.put("+49160", new ProviderConfig("T-Mobile", "0160", "tmobile-de"));
        Provider.put("+49162", new ProviderConfig("Vodafone", "0162", "vodafone-de"));
        Provider.put("+49163", new ProviderConfig("E-Plus", "01063", "eplus"));
        Provider.put("+49170", new ProviderConfig("T-Mobile", "0170", "tmobile-de"));
        Provider.put("+49171", new ProviderConfig("T-Mobile", "0171", "tmobile-de"));
        Provider.put("+49172", new ProviderConfig("Vodafone", "0172", "vodafone-de"));
        Provider.put("+49173", new ProviderConfig("Vodafone", "0173", "vodafone-de"));
        Provider.put("+49174", new ProviderConfig("Vodafone", "0174", "vodafone-de"));
        Provider.put("+49175", new ProviderConfig("T-Mobile", "0175", "tmobile-de"));
        Provider.put("+49177", new ProviderConfig("E-Plus", "0177", "eplus"));
        Provider.put("+49178", new ProviderConfig("E-Plus", "0178", "eplus"));
        //Provider.put("0176","o2-de");
        //Provider.put("0179","o2-de");

    }


    /**
     * returns the dialprefix 0171... for a given phonenumber
     */
    private String getProviderNumber(String number) {
        return number.substring(0, 6);
    }

    /**
     * returns the 'string' name of the provider of a phone number
     */
    private String getProvider(String number) {
        return (String) Provider.get(getProviderNumber(number));
    }

    /**
     * sends the message
     *
     * @throws SmsException if number/message is not set or the smash host is not reachable
     */
    public void send() throws SmsException {

        Socket SmsSocket = null;


        if (Number.length() == 0) {
            throw new SmsException("Number is not set yet. Not sending.");
        }
        if (Message.length() == 0) {
            throw new SmsException("Message is not set yet. Not sending.");
        }

        String provider = ((ProviderConfig) Provider.get(getProviderNumber(Number))).getSmashId();

        // get the correct number to pass to the service
        String numberToDial = ((ProviderConfig) Provider.get(getProviderNumber(Number))).getPrefix() +
                Number.substring(6, Number.length());


        // now try to connect to the smashd host
        try {
            InetAddress SmsServer = InetAddress.getByName(SmsHost);
            SmsSocket = new Socket(SmsServer, SmsPort);
        } catch (IOException e) {
            throw new SmsException("Could not make connection to " + SmsHost + " on port " + SmsPort + ".");
        }

        try {
            PrintStream pout = new PrintStream(SmsSocket.getOutputStream());
            pout.println("SENDMSG * SMASHP/1.0");
            pout.println("Phone: " + numberToDial);
            pout.println("Operator: " + provider);
            pout.println("Content-Length: " + Message.length());
            pout.println("User-Agent: " + UserAgent);
            pout.println("\n" + Message);         // \n is needed for the protocoll
        } catch (IOException e) {
            throw new SmsException("Could not write to the SMSServer on " + SmsHost);
        }

    }

}

