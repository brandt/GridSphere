/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.portletcontainer.descriptor;

import java.util.ArrayList;
import java.util.List;

public class SupportsModes {

    private List markupList = new ArrayList();

    public void setMarkupList(ArrayList markupList) {
        this.markupList = markupList;
    }

    public List getMarkupList() {
        return markupList;
    }
}

