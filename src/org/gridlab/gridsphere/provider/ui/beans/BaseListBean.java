/*
 * @author <a href="oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id$
 */

package org.gridlab.gridsphere.provider.ui.beans;

/**
 * The <code>BaseListBean</code>  is an implementation of BaseList.
 */
public abstract class BaseListBean extends NameBean implements BaseList {

    String name = new String();

    public abstract String toString();
}
