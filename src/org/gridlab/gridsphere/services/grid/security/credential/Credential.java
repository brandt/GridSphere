/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id$
 * <p>
 */
package org.gridlab.gridsphere.services.grid.security.credential;

import java.util.Date;

public interface Credential {

    public String getDN();

    public String getSubject();

    public String getIssuer();
    
    public long getTimeLeft();
    
    public Date getTimeExpires();

    public boolean isExpired();

    public String toString();

    public void destroy();
}
