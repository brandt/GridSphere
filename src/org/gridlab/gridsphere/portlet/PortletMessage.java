/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portlet;

/**
 * The PortletMessage can be used for inter-portlet communication.
 * A message object has to implement this interface in order to act as an information carrier between portlets.
 * The content of the message is a matter of contract between the involved portlets.
 * A portlet that broadcasts messages should publish its message object format in its documentation.
 * Inter-portlet communication can only occur inside portlet applications.
 */
public interface PortletMessage {


}
