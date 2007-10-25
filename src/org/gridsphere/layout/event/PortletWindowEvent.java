/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.layout.event;

/**
 * A <code>WindowEvent</code> is sent by a portlet window whenever the user or the portal interacts with its controls.
 */
public interface PortletWindowEvent {

    /**
     * Event identifier indicating that a portlet window will be detached.
     * <p/>
     * An event with this id is fired when the user has requested that the window be
     * detached from the rest of the portlet page.
     */
    public static final int WINDOW_DETACHED = 0;

    /**
     * Event identifier indicating that a portlet window will be maximized.
     * <p/>
     * An event with this id is fired when the user has requested that the window be maximized.
     */
    public static final int WINDOW_MAXIMIZED = 1;

    /**
     * Event identifier indicating that a portlet window will be minimized.
     * <p/>
     * An event with this id is fired when the user has requested that the window be minimized.
     */
    public static final int WINDOW_MINIMIZED = 2;

    /**
     * Event identifier indicating that a portlet window will be restored.
     * <p/>
     * An event with this id is fired when the user has requested that the window be restored to its previous size.
     */
    public static final int WINDOW_RESTORED = 3;

    /**
     * Event identifier indicating that a portlet window is closed.
     * <p/>
     * An event with this id is fired when the user has requested that the window be closed.
     */
    public static final int WINDOW_CLOSED = 4;

    /**
     * Returns the identifier of the current window event
     *
     * @return the window event identifier
     */
    public int getEventId();

}
