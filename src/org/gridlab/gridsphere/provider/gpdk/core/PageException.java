/**
 * PageException.java
 * $Id$
 */

package org.gridlab.gridsphere.provider.gpdk.core;

/**
 * A PageException is thrown when a Page fails in its execution
 */
public class PageException extends Exception {

    private String errorMessage;

    /**
     * Constructor for PageException 
     *
     * @param errorMessage a JSP error page to invoke
     */
    public PageException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Return the error message of the exception
     * 
     * @return the error message
     */
    public String getMessage() {
        return errorMessage;
    }
} 
