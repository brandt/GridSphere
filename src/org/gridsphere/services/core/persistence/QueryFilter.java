/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.services.core.persistence;

/**
 * A <code>QueryFilter</code> is used to provide support for table pagination
 */
public class QueryFilter {

    private int maxResults = 0;
    private int firstResult = 0;
    private int totalItems = 0;

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

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
    public int getTotalItems() {
        return totalItems;
    }




}
