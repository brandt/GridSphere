/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * $Id$
 */
package org.gridlab.instantmessage;

import com.echomine.jabber.*;
import com.echomine.net.ConnectionFailedException;
import com.echomine.common.SendMessageFailedException;

import java.net.UnknownHostException;

public class JabberBot {

    private static JabberBot instance;
    private final static String username = "portal";
    private final static String password = "gridlab";
    private final static String host = "194.94.224.212";
    private final static boolean DISCONNECT_AFTER_MESSAGE = true;

    private JabberSession jabberSession;

    public JabberBot() {
        JabberContext jabberContext = new JabberContext(username, password, host);
        Jabber jabber = new Jabber();
        jabberSession = jabber.createSession(jabberContext);
    }

    public static synchronized JabberBot getInstance() {
      if (null == instance) {
        instance = new JabberBot();
      }
      return instance;
    }


    private void reconnect() throws InstantMessageException {
        try {
            jabberSession.connect(host, 5222);
            jabberSession.getUserService().login();
        } catch (ConnectionFailedException e) {
            throw new InstantMessageException("Could not connect to server");
        } catch (UnknownHostException e) {
            throw new InstantMessageException("Unknown Host Error");
        } catch (JabberMessageException e) {
            throw new InstantMessageException("JabberBot Exception");
        } catch (SendMessageFailedException e) {
            throw new InstantMessageException("Could not send JabberBot");
        }

    }
    /**
     * sends the message
     * @param subject
     * @param message
     * @param recv
     * @throws InstantMessageException
     */
    public void sendMessage(String subject, String message, String recv) throws InstantMessageException {
        if(!jabberSession.getConnection().isConnected()) {
           reconnect();
        }

        JabberChatMessage msg = new JabberChatMessage(JabberChatMessage.TYPE_NORMAL);
        msg.setSubject(subject);
        msg.setBody(message);
        msg.setTo(recv+"@"+host);
        try {
            jabberSession.sendMessage(msg);
        } catch (SendMessageFailedException e) {
            throw new InstantMessageException("Could not send Message ");
        }

        if (DISCONNECT_AFTER_MESSAGE) {
            jabberSession.disconnect();
        }

    }
}
