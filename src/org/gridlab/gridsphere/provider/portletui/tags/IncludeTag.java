package org.gridlab.gridsphere.provider.portletui.tags;

import javax.servlet.ServletContext;

/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
public interface IncludeTag extends BeanTag {
    ServletContext getServletContext();

    void setServletContext(ServletContext servletContext);

    String getPage();

    void setPage(String page);
}
