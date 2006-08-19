/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PreferencesValidatorImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet.jsrimpl;

import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;
import javax.portlet.ValidatorException;


/**
 * The <CODE>PreferencesValidator</CODE> allows to validate the set of
 * preferences of the associated portlet just before they are
 * stored in the persistent store.
 * <p/>
 * The portlet container invokes the <code>validate</code> method as
 * part of the invocation of the <code>store</code> method of the
 * <code>PortletPreferences</code>.
 */
public class PreferencesValidatorImpl implements PreferencesValidator {

    /**
     * If the preferences values are successfully validated the call to this method
     * must finish gracefully. Otherwise it must throw a <code>ValidatorException</code>.
     *
     * @param preferences preferences to validate
     * @throws ValidatorException if the given preferences contains invalid
     *                            settings
     */

    public void validate(PortletPreferences preferences)
            throws ValidatorException {
        // TODO
    }
}
