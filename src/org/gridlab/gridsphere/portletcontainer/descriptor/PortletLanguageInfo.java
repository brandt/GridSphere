/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Iterator;
import java.util.Map;

public interface PortletLanguageInfo {

    public String getLocale();

    public String getTitle();

    public String getTitleShort();

    public String getDescription();

    public String[] getKeywords();
    
}
