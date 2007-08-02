package org.gridsphere.services.core.security.auth.modules.impl;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public class UserDescriptor {
    private String emailAddress;
    private String ID;
    private String userName;

    public UserDescriptor(String emailAddress, String id, String userName) {
        this.emailAddress = emailAddress;
        this.ID = id;
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getID() {
        return ID;
    }

    public String getUserName() {
        return userName;
    }
}
