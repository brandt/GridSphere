package org.gridsphere.services.core.locale;

import org.gridsphere.portlet.service.PortletService;

import java.util.Locale;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface LocaleService extends PortletService {

    public Locale[] getSupportedLocales();

}
