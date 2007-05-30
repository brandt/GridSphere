package org.gridsphere.provider.portletui.model;

import java.util.List;

/**
 * User: novotny
 * Date: May 16, 2006
 * Time: 2:45:49 PM
 */

public interface DataSource {

    public void setFirstResult(int firstResult);

    public int getFirstResult();

    public void setMaxResults(int maxResults);

    public int getMaxResults();

    public List getList();
}
