/*
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Dec 4, 2002
 * Time: 2:03:20 AM
 * To change template for new class use 
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.Portlet;

public class SportletMode {

    public static Portlet.Mode getInstance(String mode) throws Exception {
        if (mode.equalsIgnoreCase(Portlet.Mode.EDIT.toString())) {
            return Portlet.Mode.EDIT;
        } else if (mode.equalsIgnoreCase(Portlet.Mode.VIEW.toString())) {
            return Portlet.Mode.VIEW;
        } else if (mode.equalsIgnoreCase(Portlet.Mode.HELP.toString())) {
            return Portlet.Mode.HELP;
        } else if (mode.equalsIgnoreCase(Portlet.Mode.CONFIGURE.toString())) {
            return Portlet.Mode.CONFIGURE;
        } else {
            throw new Exception("Unable to create Portlet.Mode for mode: " + mode);
        }
    }
}
