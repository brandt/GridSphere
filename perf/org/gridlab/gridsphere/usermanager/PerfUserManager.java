/*
 * @author <a href="wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @versiob $Id$
 */

package org.gridlab.gridsphere.usermanager;

import org.gridlab.gridsphere.services.user.UserManagerService;
import org.gridlab.gridsphere.services.user.AccountRequest;
import org.gridlab.gridsphere.services.user.impl.AccountRequestImpl;
import org.gridlab.gridsphere.services.security.acl.AccessControlManagerService;
import org.gridlab.gridsphere.services.security.acl.AccessControlService;
import org.gridlab.gridsphere.core.persistence.castor.PersistenceManagerRdbms;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;

public class PerfUserManager {

    private static AccessControlService aclService = null;
    private static AccessControlManagerService aclManagerService = null;
    private static UserManagerService userManager = null;
    protected static SportletServiceFactory factory = null;
    //private static PersistenceManagerRdbms pm = null;
    private static PortletLog log = SportletLog.getInstance(PerfUserManager.class);

    public static void main(String[] args) {

        // Create both services using mock ServletConfig

        factory = SportletServiceFactory.getInstance();

        try {
            aclService = (AccessControlService)factory.createPortletService(AccessControlService.class, null, true);
            aclManagerService = (AccessControlManagerService)factory.createPortletService(AccessControlManagerService.class, null, true);
            userManager = (UserManagerService)factory.createPortletService(UserManagerService.class, null, true);
        } catch (Exception e) {
            log.error("Unable to initialize services: ", e);
        }
        //pm = PersistenceManagerRdbms.getInstance();
        long stopwatch_start = System.currentTimeMillis();

        perfmakeAccountRequest(500);
        perfretrieveAccountRequest();
    }


    private static void perfmakeAccountRequest(int number) {
        long mystopwatch_start = System.currentTimeMillis();
        for (int i=0;i<=number;i++) {
            AccountRequest req1 = userManager.createAccountRequest();
            req1.setUserID("jason"+i);req1.setGivenName("Jason"+i);
            try {
                userManager.submitAccountRequest(req1);
            }  catch  (PortletServiceException e) {
                log.error("Failed for "+number);
            }
        }
        long mystopwatch_stop = System.currentTimeMillis();
        long mystopwatch = mystopwatch_stop-mystopwatch_start;
        log.info("Time for creating "+number+" AccountRequests is (milisec) :"+mystopwatch);
    }


    private static void perfretrieveAccountRequest() {
        long mystopwatch_start = System.currentTimeMillis();
        List result = new Vector();
        result = userManager.getAccountRequests();
        for (int i=0;i<result.size();i++) {
            AccountRequestImpl ari = (AccountRequestImpl)result.get(i);
        }
        long mystopwatch_stop = System.currentTimeMillis();
        long mystopwatch = mystopwatch_stop-mystopwatch_start;
        log.info("Time for retrieving "+result.size()+" AccountRequests is (milisec) :"+mystopwatch);
    }

}
