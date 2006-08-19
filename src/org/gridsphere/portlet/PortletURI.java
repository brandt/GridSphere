/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletURI.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridsphere.portlet;


/**
 * A <code>PortletURI</code> represents a URI to a specific portlet function.
 * A URI is created through the
 * {@link org.gridsphere.portlet.PortletResponse} for a specific
 * portlet mode. Then additional parameter can be added to the URI, which can
 * finally be converted to a string which is ready for embedding into markup.
 * On top of the parameters, it is possible to add actions to a portlet URI.
 * Actions are portlet-specific activities that need to be performed as result
 * of the incoming request, but before the service() method of the portlet is
 * called. For example, the <code>EDIT</code> mode of the portlet is likely
 * to have a "Save" button at the end of its dialog. The "Save" button has to
 * bring the user back to the <code>VIEW</code> mode of the portlet,
 * but to save the personalized portlet data, the portlet needs to be able to
 * process the posted data before the next markup is generated. This can be
 * achieved by adding a "Save" action to the URI that represents the "Save"
 * button. The respective listener is attached the respective action listener
 * to the portlet response. This listener will be called when the next request
 * comes and one of the portlet URIs where the reason for the request. If more
 * than one URI were part of the response, the listener need to the check the
 * action content. This depends on the definition of the actual action which is
 * the responsibility of the portlet developer.
 */
public interface PortletURI {

    /**
     * Adds the given parameter to this URI. A portlet container may wish to prefix the attribute names
     * internally, to preserve a unique namespace for the portlet.
     *
     * @param name  the parameter name
     * @param value the parameter value
     */
    public void addParameter(String name, String value);

    /**
     * Adds the given action to this URI. The action is a portlet-defined
     * implementation of the portlet action interface. It can carry any
     * information. How the information is recovered should the next request
     * be for this URI is at the discretion of the portlet container.
     * <p/>
     * Unless the ActionListener interface is implemented at the portlet this
     * action will not be delivered.
     *
     * @param action the portlet action
     */
    public void addAction(PortletAction action);

    /**
     * Adds the given action to this URI.
     *
     * @param simpleAction the portlet action String
     */
    public void addAction(String simpleAction);

    /**
     * Returns the complete URI as a string. The string is ready to be
     * embedded in markup. Once the string has been created, adding more
     * parameters or other listeners will not modify the string. You have to
     * call this method again, to create an updated string.
     *
     * @return the URI as a string
     */
    public String toString();

}