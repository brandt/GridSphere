/**
 * CommandException.java
 * Encapsulates the exceptions caused by exec'ing a command
 * $Id$
 */

package org.gridlab.gridsphere.provider.gpdk.core;

public class CommandException extends Exception {
    
  public CommandException(String msg) {
      super(msg);
  }

} 
