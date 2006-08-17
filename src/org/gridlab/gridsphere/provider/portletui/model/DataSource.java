package org.gridlab.gridsphere.provider.portletui.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: May 16, 2006
 * Time: 2:58:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DataSource {

    public void setFirstResult(int firstResult);

    public int getFirstResult();

    public void setMaxResults(int maxResults);

    public int getMaxResults();

    public List getList();
}
