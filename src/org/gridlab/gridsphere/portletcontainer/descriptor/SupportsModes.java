/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.Vector;
import java.util.List;

public class SupportsModes {

    private List markupList;

    public void setMarkupList(Vector markupList) {
        this.markupList = markupList;
    }

    public List getMarkupList() {
        return markupList;
    }
}

