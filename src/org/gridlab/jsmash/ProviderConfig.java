/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @team sonicteam
 * @version $Id$
 */

package org.gridlab.jsmash;

public class ProviderConfig {

    private String Name;
    private String Prefix;
    private String SmashId;

    public ProviderConfig(String name, String prefix, String smashid) {
        Name = name;
        Prefix = prefix;
        SmashId = smashid;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSmashId() {
        return SmashId;
    }

    public void setSmashId(String smashId) {
        SmashId = smashId;
    }
}

