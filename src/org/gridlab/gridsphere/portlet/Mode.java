/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletRequest.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.portlet;

import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Comparator;
import java.io.Serializable;

/**
 * A <code>Mode</code> is an immutable representation of the portlet mode.
 * Possible mode values are <code>VIEW</code>, <code>EDIT</code>,
 * <code>HELP</code> and <code>CONFIGURE</code>
 */
public class Mode implements Comparator, Cloneable, Serializable {

    // Ordering determines title bar layout position!!
    public static final int CONFIGURE_MODE = 1;
    public static final int EDIT_MODE = 2;
    public static final int VIEW_MODE = 3;
    public static final int HELP_MODE = 4;

    public static final Mode EDIT = new Mode(EDIT_MODE, "EDIT");
    public static final Mode VIEW = new Mode(VIEW_MODE, "VIEW");
    public static final Mode HELP = new Mode(HELP_MODE, "HELP");
    public static final Mode CONFIGURE = new Mode(CONFIGURE_MODE, "CONFIGURE");

    private int mode = VIEW_MODE;
    private String modeString = "VIEW";

    /**
     * Private constructor creates pre-defined Mode objects
     *
     * @param mode the portlet mode to create
     */
    private Mode(int mode, String modeString) {
        this.mode = mode;
        this.modeString = modeString;
    }

    /**
     * Return a portlet mode from parsing a <code>String</code> representation
     * of a portlet mode.
     *
     * @throws IllegalArgumentException if the supplied <code>String</code>
     *                                  does not match a predefined portlet mode.
     */
    public static Mode toMode(String mode) throws IllegalArgumentException {
        if (mode == null) return null;
        if (mode.equalsIgnoreCase(EDIT.toString())) {
            return EDIT;
        } else if (mode.equalsIgnoreCase(VIEW.toString())) {
            return VIEW;
        } else if (mode.equalsIgnoreCase(HELP.toString())) {
            return HELP;
        } else if (mode.equalsIgnoreCase(CONFIGURE.toString())) {
            return CONFIGURE;
        }
        throw new IllegalArgumentException("Unable to parse supplied mode: " + mode);
    }

    /**
     * Returns the portlet mode as an integer
     *
     * @return id the portlet mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Returns the portlet mode as a <code>String</code>
     *
     * @return the portlet mode as a <code>String</code>
     */
    public String toString() {
        return modeString;
    }

    /**
     * Returns a locale-specific <code>String</code> representation of
     * the portlet mode.
     *
     * @return the locale-specific mode expressed as a <code>String</code>
     */
    public String getText(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        String key = toString();
        return bundle.getString(key);
    }

    public int compare(Object left, Object right) {
        int leftID = ((Mode) left).getMode();
        int rightID = ((Mode) right).getMode();
        int result;
        if (leftID < rightID) {
            result = -1;
        } else if (leftID > rightID) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    public Object clone() throws CloneNotSupportedException {
        Mode m = (Mode) super.clone();
        m.mode = this.mode;
        return m;
    }

    public boolean equals(Object o) {
        if ((o != null) && (o instanceof Mode)) {
            return (this.mode == ((Mode) o).getMode());
        }
        return false;
    }

    public int hashCode() {
        return mode;
    }

    private Object readResolve() {
        Mode m = Mode.VIEW;
        switch (mode) {
            case VIEW_MODE:
                m = Mode.VIEW;
                break;
            case CONFIGURE_MODE:
                m = Mode.CONFIGURE;
                break;
            case EDIT_MODE:
                m = Mode.EDIT;
                break;
            case HELP_MODE:
                m = Mode.HELP;
                break;
        }
        return m;
    }

}

