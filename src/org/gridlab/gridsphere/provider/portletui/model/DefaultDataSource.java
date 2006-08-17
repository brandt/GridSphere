package org.gridlab.gridsphere.provider.portletui.model;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: May 16, 2006
 * Time: 2:45:49 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class DefaultDataSource implements DataSource {

    protected int firstResult = 0;
    protected int maxResults = 0;

    public DefaultDataSource() {}

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public abstract List getList();

}
