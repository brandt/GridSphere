package org.gridsphere.layout;

import org.gridsphere.portletcontainer.GridSphereEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortletFrameRegistry {

    private static PortletFrameRegistry instance = new PortletFrameRegistry();
    private static Map<String, Map<String, PortletFrame>> portlets = new HashMap<String, Map<String, PortletFrame>>();

    private PortletFrameRegistry() {}

    public static PortletFrameRegistry getInstance() {
        return instance;
    }

    public PortletFrame getPortletFrame(String label, String portletId, GridSphereEvent event) {
        String sessionId = event.getRenderRequest().getPortletSession(true).getId();
        Map<String, PortletFrame> map = (Map<String, PortletFrame>)portlets.get(sessionId);
        PortletFrame frame = null;
        if (map != null) {
            frame = (PortletFrame)map.get(label);
            if (frame != null) return frame;
        } else {
            map = new HashMap<String, PortletFrame>();
        }
        if (portletId == null) return null;
        frame = new PortletFrame();
        frame.setPortletClass(portletId);
        frame.setLabel(label);
        frame.init(event.getRenderRequest(), new ArrayList<ComponentIdentifier>());
        map.put(label, frame);
        portlets.put(sessionId, map);
        return frame;
    }

    public void removeAllPortletFrames(GridSphereEvent event) {
        String sessionId = event.getRenderRequest().getPortletSession(true).getId();
        portlets.remove(sessionId);
    }
}
