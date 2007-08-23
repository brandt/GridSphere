package org.gridsphere.services.core.security.auth.modules;

import org.gridsphere.services.core.security.auth.modules.impl.UserDescriptor;
import org.gridsphere.services.core.security.auth.modules.impl.AuthenticationParameters;

import java.util.Map;

/**
 * @author <a href="mailto:docentt@man.poznan.pl">Tomasz Kuczynski</a>, PSNC
 * @version $Id$
 */
public interface LateUserRetrievalAuthModule extends LoginAuthModule {
    public UserDescriptor checkAuthentication(AuthenticationParameters authenticationParameters) throws Exception;
}
