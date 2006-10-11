/**
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: LDAPAuthModule.java,v 1.5 2005/06/06 12:31:12 russell Exp $
 */
package org.gridsphere.services.core.security.auth.modules.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 *
 */
public class LDAPAuthModule extends BaseAuthModule implements LoginAuthModule {

    private static Log log = LogFactory.getLog(LDAPAuthModule.class);

    private static final String SZTAKI = "ldap://n0.iit.bme.hu:389";
    private static final String SZTAKI_DN = "ou=People, dc=ikpc, dc=iit, dc=bme, dc=hu";

    public static final String GRIDLAB = "ldap://mds.gridlab.org:2135";

    public static String LDAP_HOST = "LDAP_HOST";
    public static String BASE_DN = "BASE_DN";

    public LDAPAuthModule(AuthModuleDefinition authModuleDef) {
        super(authModuleDef);
    }

    public void setLDAPHostUrl(String ldapUrl) {
        attributes.put(LDAP_HOST, ldapUrl);
    }

    public String getLDAPHostUrl() {
        return (String) attributes.get(LDAP_HOST);
    }

    public void setBaseDistinguishedName(String baseDN) {
        attributes.put(BASE_DN, baseDN);
    }

    public String getBaseDistinguishedName() {
        return (String) attributes.get(BASE_DN);
    }

    public void checkAuthentication(User user, String password) throws AuthenticationException {

        Hashtable env = new Hashtable(5);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, getLDAPHostUrl());
        String name = user.getUserName();
        try {

            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "uid=" + name + ", " + getBaseDistinguishedName());
            env.put(Context.SECURITY_CREDENTIALS, password);

            // Create initial context
            DirContext ctx = new InitialDirContext(env);

            // Close the context when we're done
            ctx.close();
        } catch (javax.naming.AuthenticationException e) {
            throw new AuthenticationException("User: " + user.getUserName() + " unable to bind under provided name and password");
        } catch (NamingException e) {
            log.error("Error occurred querying LDAP: ", e);
            throw new AuthenticationException("User " + user.getUserName() + " not authorized under provided name and password");
        }

    }

}