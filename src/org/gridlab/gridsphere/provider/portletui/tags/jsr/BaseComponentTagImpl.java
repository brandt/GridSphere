/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.provider.portletui.tags.jsr;

import org.gridlab.gridsphere.provider.portletui.tags.ComponentTag;
import org.gridlab.gridsphere.provider.portletui.tags.BaseComponentTag;

import javax.portlet.RenderRequest;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The abstract <code>BaseComponentTag</code> is used by all UI tags to provide CSS support and general
 * name, value attributes
 */
public abstract class BaseComponentTagImpl extends BaseComponentTag implements ComponentTag {

    protected String getLocalizedText(String key) {
        return getLocalizedText(key, "Portlet");
    }

    protected String getLocalizedText(String key, String base) {
        RenderRequest req = (RenderRequest)pageContext.getAttribute("renderRequest");
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(base, locale);
        return bundle.getString(key);
    }
}
