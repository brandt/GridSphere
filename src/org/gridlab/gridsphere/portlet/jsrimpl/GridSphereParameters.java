/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.jsrimpl;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: Apr 3, 2004
 * Time: 12:59:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class GridSphereParameters {

    private HttpServletRequest req = null;
    private Map renderParams = null;
    private Map params = null;
    private String mycid = null;
    private String targetedCid = null;
    private List reservedParams = null;

    public GridSphereParameters(HttpServletRequest request) {
        this.req = request;

        // check request string for action
        String queryString = req.getQueryString();

        params = new HashMap();
        renderParams = new HashMap();

        // create reserved params list
        reservedParams = new ArrayList();
        reservedParams.add(SportletProperties.COMPONENT_ID);
        reservedParams.add(SportletProperties.DEFAULT_PORTLET_ACTION);
        reservedParams.add(SportletProperties.PORTLET_MODE);
        reservedParams.add(SportletProperties.PORTLET_WINDOW);

        parseQueryString(queryString);

        this.targetedCid = request.getParameter(SportletProperties.COMPONENT_ID);
        this.mycid = (String) request.getAttribute(SportletProperties.COMPONENT_ID);
    }

    public void parseQueryString(String queryString) {

        queryString = queryString + "&";

        StringTokenizer st = new StringTokenizer(queryString, "&");

        while (st.hasMoreTokens()) {
            String namevar = (String) st.nextElement();

            StringTokenizer st2 = new StringTokenizer(namevar, "=");
            String name = (String) st2.nextElement();
            String value = "";
            if (st2.hasMoreElements()) {
                value = (String) st2.nextElement();
            }

            name = URLDecoder.decode(name);
            value = URLDecoder.decode(value);

            if (name.startsWith(SportletProperties.RENDER_PARAM_PREFIX)) {
                String sname = name.substring(3);
                if (renderParams.containsKey(sname)) {
                    String[] s = (String[]) renderParams.get(sname);
                    String[] tmp = new String[s.length + 1];
                    System.arraycopy(s, 0, tmp, 0, s.length);
                    tmp[s.length] = value;
                    renderParams.put(sname, tmp);
                } else {
                    renderParams.put(sname, new String[]{value});
                }
            } else {
                if (!reservedParams.contains(name)) {
                    if (params.containsKey(name)) {
                        String[] s = (String[]) params.get(name);
                        String[] tmp = new String[s.length + 1];
                        System.arraycopy(s, 0, tmp, 0, s.length);
                        tmp[s.length] = value;
                        params.put(name, tmp);
                    } else {
                        params.put(name, new String[]{value});
                    }
                }
            }

        }
    }

    private void overlayRequestParams() {
        for (Enumeration parameters = req.getParameterNames(); parameters.hasMoreElements();) {
            String paramName = (String) parameters.nextElement();
            String[] paramValues = (String[]) req.getParameterValues(paramName);
            if (!reservedParams.contains(paramName)) {
                if (paramName.startsWith(SportletProperties.RENDER_PARAM_PREFIX)) {
                    String name = paramName.substring(3);
                    if (!renderParams.containsKey(name)) {
                        renderParams.put(name, paramValues);
                    }
                } else {
                    if (!params.containsKey(paramName)) {
                        params.put(paramName, paramValues);
                    }
                }
            }
        }
    }

    public Map getParameterMap() {

        Map map = new HashMap();
        // we need to distinguish between a render invocation abnd a render that follows an action
        // In the first case, all params are returned. In the second case, params in action method
        // must not be returned

        // this is a render event
        if (req.getAttribute(SportletProperties.PORTLET_ACTION_METHOD) == null) {

            // this is a render that has occured after an action
            if (req.getParameter(SportletProperties.DEFAULT_PORTLET_ACTION) != null) {
                overlayRequestParams();
                if (mycid.equals(targetedCid)) map.putAll(renderParams);
                return Collections.unmodifiableMap(map);
            }
        }

        // this is a render triggered by a render URL or an action event
        overlayRequestParams();
        map.putAll(params);
        if (mycid.equals(targetedCid)) {
            Iterator it = renderParams.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                String[] renderVals = (String[]) renderParams.get(key);
                if (map.containsKey(key)) {
                    String[] vals = (String[]) map.get(key);
                    String[] tmp = new String[vals.length + renderVals.length];
                    System.arraycopy(vals, 0, tmp, 0, vals.length);
                    System.arraycopy(renderVals, 0, tmp, vals.length, renderVals.length);
                    map.put(key, tmp);
                } else {
                    map.put(key, renderVals);
                }

            }
        }
        return Collections.unmodifiableMap(map);
    }

}
