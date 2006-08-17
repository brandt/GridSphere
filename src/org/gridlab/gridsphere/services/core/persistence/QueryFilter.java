package org.gridlab.gridsphere.services.core.persistence;

/**
 * Created by IntelliJ IDEA.
 * User: novotny
 * Date: May 15, 2006
 * Time: 10:36:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryFilter {

    private int maxResults = 0;
    private int firstResult = 0;

    public QueryFilter() {}

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public int getFirstResult() {
        return firstResult;
    }

}
