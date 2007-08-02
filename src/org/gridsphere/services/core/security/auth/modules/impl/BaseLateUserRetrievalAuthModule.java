package org.gridsphere.services.core.security.auth.modules.impl;

import org.gridlab.gridsphere.services.core.security.auth.modules.impl.BaseAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.portlet.User;
import org.gridsphere.services.core.security.auth.modules.LateUserRetrievalAuthModule;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public abstract class BaseLateUserRetrievalAuthModule extends BaseAuthModule implements LateUserRetrievalAuthModule {
    public BaseLateUserRetrievalAuthModule(Object moduleDef) {
        super((AuthModuleDefinition)moduleDef);
    }

    public void checkAuthentication(User user, String password) throws AuthenticationException {
        throw new AuthenticationException("unsupported");
    }

    public void throwAuthenticationException(String message) throws AuthenticationException {
        throw new AuthenticationException(message);
    }
}
