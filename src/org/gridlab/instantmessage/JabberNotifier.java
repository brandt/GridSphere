/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * $Id$
 */
package org.gridlab.instantmessage;

// later we maybe going to have a notification framework

public class JabberNotifier {

    private JabberBot bot;

    public JabberNotifier() {
        bot = JabberBot.getInstance();
    }

    public void notify(String subject, String message, String user) throws InstantMessageException {
        bot.sendMessage(" ", message, user);
    }
}
