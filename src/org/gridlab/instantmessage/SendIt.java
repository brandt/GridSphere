/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * $Id$
 */
package org.gridlab.instantmessage;

public class SendIt {
    public static void main(String[] args) {

        JabberNotifier not = new JabberNotifier();

        try {
            not.notify("header", "subjesssct","owehrens");
        } catch (InstantMessageException e) {
            e.printStackTrace();  //To change body of catch statement use Options | File Templates.
        }

    }
}
