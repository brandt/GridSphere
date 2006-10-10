package org.gridsphere.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
public interface PortalFilter {

    public void init(PortalFilterConfig config);

    public void doAfterLogin(HttpServletRequest req, HttpServletResponse res);

    public void doAfterLogout(HttpServletRequest req, HttpServletResponse res);

    public void doBeforeEveryRequest(HttpServletRequest req, HttpServletResponse res);

    public void doAfterEveryRequest(HttpServletRequest req, HttpServletResponse res);

}
