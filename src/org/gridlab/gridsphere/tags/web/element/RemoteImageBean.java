/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.tags.web.element;

public class RemoteImageBean extends ActionLinkBean {

    public String toString() {
        return "<img title='"+getText()+"' alt='"+getText()+"' src='" + link + "'/>";
    }
}
