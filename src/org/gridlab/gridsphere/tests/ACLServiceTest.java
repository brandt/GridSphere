/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id$
 */
package org.gridlab.gridsphere.tests;

import org.gridlab.gridsphere.services.AccessControlService;
import org.gridlab.gridsphere.services.AccessControlManagerService;
import org.gridlab.gridsphere.services.ServletParsingService;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.PortletRoles;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletUser;
import org.gridlab.gridsphere.portlet.impl.SportletUserImpl;

import java.util.Properties;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;

public class ACLServiceTest {

    private static Properties props = new Properties();
    private static SportletServiceFactory factory = SportletServiceFactory.getInstance();
    private static AccessControlService aclService = null;
    private static AccessControlManagerService aclManagerService = null;

    public static final int NUMGROUPS = 10;
    public static final int NUMPEOPLE = 10;

    public static void main(String[] args) {
        int i, j;
        Iterator it;

        System.err.println(args[0]);
        FileInputStream fistream = null;
        String fullPath = "PortletServices.properties";
        try {
            fistream = new FileInputStream(fullPath);
        } catch (FileNotFoundException e) {
            System.err.println("Can't find file: " + fullPath);
        }

        try {
            props.load(fistream);
        } catch (IOException e) {
            System.err.println("can't load properties file");
        }

        try {
            aclService = (AccessControlService)factory.createPortletService(AccessControlService.class, props, null, true);
            aclManagerService =
                (AccessControlManagerService)factory.createPortletService(AccessControlManagerService.class, props, null, true);
        } catch (Exception e) {
            System.err.println("can't instantiate access services");
        }

        // Create a bunch of groups
        for (i = 0; i < NUMGROUPS; i++) {
            aclManagerService.createNewGroup("group " + i);
        }

        // Create a bunch of people in groups
        SportletUser[] user = new SportletUserImpl[NUMPEOPLE*NUMGROUPS];
        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i++) {
                user[10*j+i] = new SportletUserImpl();
                user[10*j+i].setFullName("person " + (10*j+i));
                user[10*j+i].setID("uid: " + (10*j+i));
            }
        }

        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i++) {
                aclManagerService.addUserToGroup(user[10*j+i], "group " + j, PortletRoles.USER);
            }
        }

        // Print out all users in each group
        for (j = 0; j < NUMGROUPS; j++) {
            it = aclService.getUserNamesInGroup("group " + j);
            System.err.println("users in group " + j);
            while (it.hasNext()) {
                System.err.println(it.next());
            }
        }

        // Give more roles

        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i=i+2) {
                aclManagerService.addRoleInGroup(user[10*j+i], "group " + j, PortletRoles.ADMIN);
            }
        }

        // Check roles
        int num = 0;
        for (j = 0; j < NUMGROUPS; j++) {
            for (i = 0; i < NUMPEOPLE; i++) {
                if (aclService.hasRoleInGroup(user[10*j+i], "group " + j, PortletRoles.ADMIN)) num++;
            }
        }
        System.err.println("number = " + num);

        it = aclService.getAllGroupNames();
        while (it.hasNext()) {
            System.err.println(it.next());
        }

        it = aclService.getAllUserNames();
        while (it.hasNext()) {
            System.err.println(it.next());
        }

        factory.shutdownServices();
    }


}
