/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.layout;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;

public class PortletPanel extends BasePortletComponent {

    private static PortletLog log = org.gridlab.gridsphere.portlet.impl.SportletLog.getInstance(PortletPanel.class);

    private LayoutManager layoutManager;

    public PortletPanel() {}

    public String getClassName() {
        return PortletPanel.class.getName();
    }

    public List init(List list) {
        log.info("in init()");
        this.id = list.size();
        PortletLifecycle cycle = (PortletLifecycle)layoutManager;
        list.add(cycle);
        list = cycle.init(list);
        return list;
    }

    public void destroy() {
        layoutManager.destroy();
    }

    public PortletPanel(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void actionPerformed(GridSphereEvent event) throws PortletLayoutException, IOException {
        layoutManager.actionPerformed(event);
    }

    public void doRender(GridSphereEvent event) throws PortletLayoutException, IOException {
        layoutManager.doRender(event);
    }

}
