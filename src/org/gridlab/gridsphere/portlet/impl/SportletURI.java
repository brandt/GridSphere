/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet.impl;

import org.gridlab.gridsphere.portlet.PortletURI;
import org.gridlab.gridsphere.portlet.PortletAction;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portletcontainer.GridSphereProperties;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * A PortletURI represents a URI to a specific portlet function.
 * A URI is created through the PortletResponse  for a specific portlet mode.
 * Then additional parameter can be added to the URI.
 * The complete URI can be converted to a string which is ready for embedding into markup.
 * On top of the parameters, it is possible to add actions to a portlet URI.
 * Actions are portlet-specific activities that need to be performed as result of the incoming request,
 * but before the service() method of the portlet is called.
 * For example, the PERSONALIZE mode of the portlet is likely to have a "Save" button at the
 * end of its dialog. The "Save" button has to bring the user back to the DEFAULT mode of the portlet,
 * but to save the personalized portlet data, the portlet needs to be able to process the posted
 * data before the next markup is generated. This can be achieved by adding a "Save" action to the
 * URI that represents the "Save" button. The respective listener is attached the respective action listener
 * to the portlet response. This listener will be called when the next request comes and one of the
 * portlet URIs where the reason for the request. If more than one URI were part of the response, the listener
 * need to the check the action content. This depends on the definition of the actual action which is
 * the responsibility of the portlet developer.
 */
public class SportletURI implements PortletURI {

    private HttpServletResponse res = null;
    private Map store;
    private boolean redirect = true;
    private String url;

    public SportletURI(HttpServletResponse res, String servletName, boolean redirect) {
        store = new HashMap();
        this.res = res;
        this.redirect = redirect;
        url = servletName;
    }

    /**
     * Adds the given parameter to this URI. A portlet container may wish to prefix the attribute names
     * internally, to preserve a unique namespace for the portlet.
     *
     * @param name the parameter name
     * @param value the parameter value
     */
    public void addParameter(String name, String value) {
        store.put(name, value);
    }

    /**
     * Adds the given action to this URI. The action is a portlet-defined implementation of the portlet action
     * interface. It can carry any information. How the information is recovered should the next request be for
     * this URI is at the discretion of the portlet container.
     *
     * Unless the ActionListener interface is implemented at the portlet this action will not be delivered.
     *
     * @param action the portlet action
     */
    public void addAction(PortletAction action) {
        // XXX: FIX ME HOW THE HELL DO ACTIONS GET SERIALIZED INTO A URI?
        // SINCE ALL RELEVANT STUFF IS STRINGS WE CAN PROBABLY JUST INTROSPECT A
        if (action instanceof DefaultPortletAction) {
            DefaultPortletAction dpa = (DefaultPortletAction)action;
            store.put(GridSphereProperties.PORTLETNAME, dpa.getName());
            store.put(GridSphereProperties.PORTLETID, dpa.getPortletID());
        }
    }

    /**
     * Returns the complete URI as a string. The string is ready to be embedded in markup.
     * Once the string has been created, adding more parameters or other listeners will not modify the string.
     * You have to call this method again, to create an updated string.
     *
     * @return the URI as a string
     */
    public String toString() {
        Set set = store.keySet();
        if (!set.isEmpty()) {
            // add question mark
            url += "?";
        }
        while (set.iterator().hasNext()) {
            String name = (String)set.iterator().next();
            String value = (String)store.get(name);
            url += name + "&" + value;
        }
        if (redirect) {
            return res.encodeRedirectURL(url);
        } else {
            return res.encodeURL(url);
        }
    }

}