package org.gridsphere.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public abstract class BasePortalFilter implements PortalFilter {

    public abstract void init(PortalFilterConfig filterConfig);

    public abstract void doAfterLogin(HttpServletRequest req, HttpServletResponse res);

    public abstract void doAfterLogout(HttpServletRequest req, HttpServletResponse res);

    public abstract void doBeforeEveryRequest(HttpServletRequest req, HttpServletResponse res);

    public abstract void doAfterEveryRequest(HttpServletRequest req, HttpServletResponse res);
}
