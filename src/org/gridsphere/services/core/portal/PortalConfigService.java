/*
 * @author <a href="mailto:kisg@mailbox.hu">Gergely Kis</a>
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id: PortalConfigService.java 5032 2006-08-17 18:15:06Z novotny $
 */
package org.gridsphere.services.core.portal;

import java.io.IOException;


/**
 * Portal configuration service is used to manage portal administrative settings
 */
public interface PortalConfigService {

    public static String PORTAL_ADMIN_EMAIL = "PORTAL_ADMIN_EMAIL";
    public static String USE_HTTPS_LOGIN = "USE_HTTPS_LOGIN";
    public static String USE_HTTPS_REDIRECT = "USE_HTTPS_REDIRECT";
    public static String MAIL_SERVER = "MAIL_SERVER";
    public static String MAIL_PORT = "MAIL_PORT";
    public static String MAIL_FROM = "MAIL_ADDRESS";
    public static String ENABLE_ERROR_HANDLING = "ENABLE_ERROR_HANDLING";
    public static String DEFAULT_THEME = "DEFAULT_THEME";
    public static String SAVE_PASSWORDS = "SAVE_PASSWORDS";
    public static String SUPPORT_X509_AUTH = "SUPPORT_X509_AUTH";
    public static String SEND_USER_FORGET_PASSWORD = "SEND_USER_FORGET_PASSWD";
    public static String REMEMBER_USER = "REMEMBER_USER";
    public static String LOGIN_NUMTRIES = "ACCOUNT_NUMTRIES";
    public static String CAN_USER_CREATE_ACCOUNT = "CAN_USER_CREATE_ACCOUNT";
    public static String ADMIN_ACCOUNT_APPROVAL = "ADMIN_ACCOUNT_APPROVAL";
    public static String USE_USERNAME_FOR_LOGIN = "USE_USERNAME_FOR_LOGIN";
    public static String USE_CAPTCHA = "USE_CAPTCHA";

    public static String PORTAL_PORT = "gridsphere.port.http";
    public static String PORTAL_SECURE_PORT = "gridsphere.port.https";
    public static String PORTAL_HOST = "gridsphere.host";

    public String getProperty(String key);

    public void setProperty(String key, String value);

    public void storeProperties() throws IOException;

}
