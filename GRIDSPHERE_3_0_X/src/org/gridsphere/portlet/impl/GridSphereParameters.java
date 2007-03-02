/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: GridSphereParameters.java 4988 2006-08-04 09:57:48Z novotny $
 */
package org.gridsphere.portlet.impl;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * GridSphereParameters is the dragon's end. A sick, twisted filter for request parameters owing to
 * the various rules that must be obeyed in the spec. Initialized in PortletRequestImpl constructor.
 */
public class GridSphereParameters {

    private Map<String, String[]> origParams = null;
    private Map<String, String[]> renderParams = null;
    private Map<String, String[]> persistParams = null;
    private Map<String, String[]> params = null;
    private List<String> reservedParams = null;


    public GridSphereParameters(Map<String, String[]> origParams) {
        this.origParams = origParams;
    }

    public void addRenderParams(Map<String, String[]> params) {
        persistParams.putAll(params);
    }

    public Map<String, String[]> parseQueryString(String queryString, boolean checkForRenderParams) {

        queryString = queryString + "&";

        //System.err.println("GP: queryString= " + queryString);

        StringTokenizer st = new StringTokenizer(queryString, "&");
        Map<String, String[]> tmpParams = new HashMap<String, String[]>();
        while (st.hasMoreTokens()) {
            String namevar = (String) st.nextElement();

            StringTokenizer st2 = new StringTokenizer(namevar, "=");
            String name = (String) st2.nextElement();
            String value = "";
            if (st2.hasMoreElements()) {
                value = (String) st2.nextElement();
            }

            try {
                name = URLDecoder.decode(name, "UTF-8");
                value = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.err.println("name= " + " value= " + value);
                e.printStackTrace();
            }

            if (checkForRenderParams) {
                if (name.startsWith(SportletProperties.RENDER_PARAM_PREFIX)) {
                    String sname = name.substring(3);
                    if (tmpParams.containsKey(sname)) {
                        String[] s = (String[]) tmpParams.get(sname);
                        String[] tmp = new String[s.length + 1];
                        System.arraycopy(s, 0, tmp, 0, s.length);
                        tmp[s.length] = value;
                        tmpParams.put(sname, tmp);
                    } else {
                        tmpParams.put(sname, new String[]{value});
                    }
                }
            } else {
                if (!reservedParams.contains(name)) {
                    if (tmpParams.containsKey(name)) {
                        String[] s = (String[]) tmpParams.get(name);
                        String[] tmp = new String[s.length + 1];
                        System.arraycopy(s, 0, tmp, 0, s.length);
                        tmp[s.length] = value;
                        tmpParams.put(name, tmp);
                    } else {
                        tmpParams.put(name, new String[]{value});
                    }
                }
            }
        }
        return tmpParams;
    }

    private void parseRequestParams() {
        //System.err.println("GP: parse req parms");
        for (String paramName : origParams.keySet()) {
            String[] paramValues = (String[]) origParams.get(paramName);
            if (!reservedParams.contains(paramName)) {
                if (paramName.startsWith("pr_" + SportletProperties.RENDER_PARAM_PREFIX)) continue;
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

    /**
     * Parses the request for double prefixed render params that have been transmitted from the PortletFrame
     * using JSRApplicationPortletImpl to get its dispatcher.
     */
    private void parsePersistParams() {
        //System.err.println("GP: parse persist parms");
        for (String paramName : origParams.keySet()) {
            String[] paramValues = (String[]) origParams.get(paramName);
            if (!reservedParams.contains(paramName)) {
                if (paramName.startsWith("pr_" + SportletProperties.RENDER_PARAM_PREFIX)) {
                    String name = paramName.substring(6);
                    if (!persistParams.containsKey(name)) {
                        persistParams.put(name, paramValues);
                    }
                }
            }
        }
    }

    public Map getParameterMap(HttpServletRequest req, String queryString) {
        // A Magic parameter to ignore the special parsing required for JSR 168
        if (req.getAttribute(SportletProperties.IGNORE_PARSING) != null) return origParams;

        // check request string for action

        params = new HashMap<String, String[]>();
        renderParams = new HashMap<String, String[]>();
        persistParams = new HashMap<String, String[]>();

        // create reserved params list
        reservedParams = new ArrayList<String>();


        reservedParams.add(SportletProperties.COMPONENT_ID);
        reservedParams.add(SportletProperties.DEFAULT_PORTLET_ACTION);
        reservedParams.add(SportletProperties.PORTLET_MODE);
        reservedParams.add(SportletProperties.PORTLET_WINDOW);

        renderParams.putAll(parseQueryString(queryString, true));
        params.putAll(parseQueryString(queryString, false));

        String targetedCid = null;
        String[] tCid = (String[]) origParams.get(SportletProperties.COMPONENT_ID);
        if (tCid != null) targetedCid = tCid[0];

        //System.err.println("target cid= " + targetedCid);

        String mycid = (String) req.getAttribute(SportletProperties.COMPONENT_ID);
        if (mycid == null) {
            //System.err.println("mycid is null");
            return origParams;
        }

        Map<String, String[]> map = new HashMap<String, String[]>();

        // check for any query params from an included JSP
        queryString = (String) req.getAttribute("javax.servlet.include.query_string");
        if (queryString != null) {
            //System.err.println("found query string");
            map.putAll(parseQueryString(queryString, false));
        }

        // we need to distinguish between a render invocation abnd a render that follows an action
        // In the first case, all params are returned. In the second case, params in action method
        // must not be returned

        //System.err.println("\nGP: in parameters  mycid= " + mycid + " targetid= " + targetedCid);

        // this is a render event (meaning params are being queried in a render method)
        if (req.getAttribute(SportletProperties.PORTLET_ACTION_METHOD) == null) {

            // this is a render that has occured after an action
            if (origParams.containsKey(SportletProperties.DEFAULT_PORTLET_ACTION)) {

                //System.err.println("GP: default action not null");

                // this is the portlet that was being targeted, now during a render
                if (mycid.equals(targetedCid)) {
                    //System.err.println("\nGP: in render event, have an action, this is the target portlet: ");
                    parseRequestParams();

                    parsePersistParams();
                    map.putAll(persistParams);
                    map.putAll(renderParams);

                } else {
                    //System.err.println("\nGP: in render event, have an action, this portlet is not targeted");
                    parsePersistParams();
                    map.putAll(persistParams);

                }
                return Collections.unmodifiableMap(map);
            }
        }

        // this is a render triggered by a render URL or an action event
        if (mycid.equals(targetedCid)) {

            parsePersistParams();

            map.putAll(persistParams);

            //System.err.println("\nGP: in render, no action this IS the targeted portlet ");

            parseRequestParams();

            // replace any persist params with render params
            for (Object o : renderParams.keySet()) {
                String key = (String) o;
                String[] paramVals = (String[]) renderParams.get(key);

                if (map.containsKey(key)) {
                    map.put(key, paramVals);
                }

            }

            // a param of the same name should take precedence over a render/persist param
            // and new render params of the same name should be added to the array
            for (Object o1 : params.keySet()) {
                String key = (String) o1;
                String[] paramVals = (String[]) params.get(key);
                if (map.containsKey(key)) {
                    String[] vals = (String[]) map.get(key);
                    String[] tmp = new String[vals.length + paramVals.length];
                    System.arraycopy(paramVals, 0, tmp, 0, paramVals.length);
                    System.arraycopy(vals, 0, tmp, paramVals.length, vals.length);
                    map.put(key, tmp);
                } else {
                    map.put(key, paramVals);
                }
            }
        } else {
            //System.err.println("GP: in render, no action this is NOT the targeted portlet");
            parsePersistParams();
            map.putAll(persistParams);
        }

        //printAllParams(map);

        return Collections.unmodifiableMap(map);
    }


    private void printAllParams(Map map) {
        System.err.println("normal params");
        for (Object o : params.keySet()) {
            String key = (String) o;
            String[] vals = (String[]) params.get(key);
            System.err.print("name= " + key + " values= ");
            for (int c = 0; c < vals.length; c++) {
                System.err.print(vals[c] + " ");
            }
        }
        System.err.println("\nrender params");
        for (Object o1 : renderParams.keySet()) {
            String key = (String) o1;
            String[] vals = (String[]) renderParams.get(key);
            System.err.print("name= " + key + " values= ");
            for (int c = 0; c < vals.length; c++) {
                System.err.print(vals[c] + " ");
            }
        }
        System.err.println("\npersist params");
        for (Object o2 : persistParams.keySet()) {
            String key = (String) o2;
            String[] vals = (String[]) persistParams.get(key);
            System.err.print("name= " + key + " values= ");
            for (int c = 0; c < vals.length; c++) {
                System.err.print(vals[c] + " ");
            }
        }
        System.err.println("\ngetParamaterMap: returning params for this portlet");
        for (Object o3 : map.keySet()) {
            String key = (String) o3;
            String[] vals = (String[]) map.get(key);
            System.err.print("name= " + key + " values= ");
            for (int c = 0; c < vals.length; c++) {
                System.err.print(vals[c] + " ");
            }
        }

    }
}
