/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.services.user.impl;

import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;
import org.gridlab.gridsphere.services.user.AccountRequest;

import java.util.Vector;

/**
 * The UserData class contains a Vector of SportletUsers consisting of the editable user profiles
 * that have accounts on the portal and a Vector of AccountRequests consisting of requests for new
 * accounts that are pending for administrative approval. Only one instance of the UserData may be obtained
 */
public class UserData {

    private Vector users = null;
    private Vector accountRequests = null;

    public UserData() {
        users = new Vector();
        accountRequests = new Vector();
    }

    public SportletUser createUser() {
        return new SportletUserImpl();
    }

    public Vector getUsers() {
        return users;
    }

    public void addUser(SportletUser user) {
        users.add(user);
    }

    public void removeUser(SportletUser user) {
        if (users.contains(user)) {
            users.remove(user);
        }
    }

    public void addAccountRequest(AccountRequest request) {
        accountRequests.add(request);
    }

    public Vector getAccountRequests() {
        return accountRequests;
    }

    public void removeAccountRequest(AccountRequest request) {
        if (accountRequests.contains(request)) {
            accountRequests.remove(request);
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < users.size(); i++) {
            sb.append(users.get(i).toString());
        }
        return sb.toString();
    }

}
