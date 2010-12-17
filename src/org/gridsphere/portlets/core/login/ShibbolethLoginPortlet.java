package org.gridsphere.portlets.core.login;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gridsphere.layout.PortletPageFactory;
import org.gridsphere.portlet.impl.PortletURLImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.services.core.persistence.PersistenceManagerService;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.security.auth.AuthModuleService;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.security.role.PortletRole;
import org.gridsphere.services.core.security.role.RoleManagerService;
import org.gridsphere.services.core.shibboleth.ShibbolethUser;
import org.gridsphere.services.core.shibboleth.ShibbolethUserService;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;
import org.gridsphere.portlets.util.ShibbolethProperties;

public class ShibbolethLoginPortlet extends ActionPortlet {
	
	private Log log = LogFactory.getLog(ShibbolethLoginPortlet.class);
	
	private UserManagerService userManagerService = null;
	private PortalConfigService portalConfigService = null;
	private AuthModuleService authModuleService = null;
	private PasswordManagerService passwordManagerService = null;
	private RoleManagerService roleManagerService = null;
	private PersistenceManagerService pms = null;
	private ShibbolethUserService shibService = null;
	
	private String redirectURL = null;
	public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
	
	public static String SHIB_PROPERTIES = null;
	
	/**
	 *  init(PortletConfig config) initialisiert die benoetigten GridSphere Core Portlet Services
	 *  Setzt DEFAULT_VIEW_PAGE
	 */
	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		
		SHIB_PROPERTIES = config.getPortletContext().getRealPath("Shibboleth.properties");
				
		userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
		portalConfigService = (PortalConfigService) createPortletService(PortalConfigService.class);
		authModuleService = (AuthModuleService) createPortletService(AuthModuleService.class);
		passwordManagerService = (PasswordManagerService) createPortletService(PasswordManagerService.class);
		roleManagerService = (RoleManagerService) createPortletService(RoleManagerService.class);
		pms = (PersistenceManagerService) createPortletService(PersistenceManagerService.class);
		shibService = (ShibbolethUserService) createPortletService(ShibbolethUserService.class);
		
		DEFAULT_VIEW_PAGE = "doViewUser";
	}
	
	
	/**
	 *  doViewUser(RenderFormEvent event) prueft ob das ShibbolethAuthModule aktiviert ist ( im Portal unter Administration -> 
	 *  Configuration -> Authentication Moduls), 
	 *  wenn ja, wird im RenderRequest das Attribute "shibboleth.login.enabled" auf "true" gesetzt und mit setNextState 
	 *  shibbolethLogin.jsp aufgerufen
	 * 
	 */
	public void doViewUser(RenderFormEvent event) throws PortletException {
		log.debug(">>>>>>>>>>>>>> in ShibbolethLoginPortlet : doViewUser");
				
		PortletRequest request = event.getRenderRequest();
		
		List modules = authModuleService.getActiveAuthModules();
		Iterator it = modules.iterator();
				
		RenderResponse response = event.getRenderResponse();
		
		if (redirectURL == null) {
            PortletURL url = response.createRenderURL();
            ((PortletURLImpl) url).setLayout(PortletPageFactory.USER_PAGE);
            ((PortletURLImpl) url).setEncoding(false);
            redirectURL = url.toString();
        }
		
		while ( it.hasNext() ) {
			LoginAuthModule module = (LoginAuthModule) it.next();
			if ( (module != null) && module.getModuleName().equals("Shibboleth Authentication") ) {
				request.setAttribute("shibboleth.login.enabled", "true");
				request.setAttribute("shibboleth.sp", ShibbolethProperties.getInstance().getProperty("host.dns"));
				break;
			}
		}
		
		String errorMsg = (String) request.getPortletSession(true).getAttribute(LOGIN_ERROR_FLAG);
		log.debug("\n\nString errorMsg = " + errorMsg);
        if (errorMsg != null) {
            createErrorMessage(event, errorMsg);
            request.getPortletSession(true).removeAttribute(LOGIN_ERROR_FLAG);
        }
		
		setNextState(request, "login/shibbolethLogin.jsp");
	}
	
		
	/*
	 * obtainSAMLAttributes(ActionFormEvent event) is called from "shibbolethLogin.jsp". 
	 * 
	 */
	public void obtainSAMLAttributes(ActionFormEvent event) throws PortletException {
		PortletRequest req = event.getActionRequest();
		setNextState(req, "login/obtainSAMLAttributes.jsp");
	
	}
	
	
	/*
	 *  gs_login(ActionFormEvent event) 
	 * 
	 */
	public void gs_login(ActionFormEvent event) throws PortletException {
		log.debug(">>>>>>>>>>>>>  in ShibbolethLoginPortlet : gs_login");
		ActionRequest req = event.getActionRequest();
		HttpServletRequest request = (HttpServletRequest) req;
				
		try {
            log.debug("##########>>>>>>>>>> in LoginPortlet gs_login Aufruf von login(ActionFormEvent event), event = " + event);
        	login(event);
        } catch (AuthorizationException err) {
        	log.debug("shibboleth.user.somevalue = " + request.getSession(true).getAttribute("shibboleth.user.somevalue"));
        	log.debug(err.getMessage());
            req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
        	err.printStackTrace();
        } catch (AuthenticationException err) {
            log.debug(err.getMessage());
            req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
            err.printStackTrace();
        }
        
        setNextState(req, DEFAULT_VIEW_PAGE);
	}
		
	

    /**
     * Handles login requests
     *
     * @param event a <code>GridSphereEvent</code>
     * @throws org.gridsphere.services.core.security.auth.AuthenticationException
     *          if auth fails
     * @throws org.gridsphere.services.core.security.auth.AuthorizationException
     *          if authz fails
     */
    protected void login(ActionFormEvent event) throws AuthenticationException, AuthorizationException {
    	
        ActionRequest req = event.getActionRequest();
        ActionResponse res = event.getActionResponse();
        
        User user = null;
		user = login(req);
				
        Long now = Calendar.getInstance().getTime().getTime();
        user.setLastLoginTime(now);
        Integer numLogins = user.getNumLogins();
        if (numLogins == null) numLogins = 0;
        numLogins++;

        user.setNumLogins(numLogins);
        log.debug(">>>>>>>>>>>>>>>>>>>>>> Setzt numLogins fuer User");
        user.setAttribute(PortalConfigService.LOGIN_NUMTRIES, "0");

        userManagerService.saveUser(user);
        
        String query = event.getAction().getParameter("queryString");
        log.debug(">>>>>>>>>>>>>>>>>>>>>> queryString = " + event.getAction().getParameter("queryString"));
        if (query != null) {
            //redirectURL.setParameter("cid", query);
        }
        //req.setAttribute(SportletProperties.LAYOUT_PAGE, PortletPageFactory.USER_PAGE);


        String realuri = null;
        Boolean useSecureRedirect = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.USE_HTTPS_REDIRECT));
        if (useSecureRedirect.booleanValue()) {
            if(redirectURL.startsWith("https")){
                realuri = redirectURL;
            }else{
                realuri = "https" + redirectURL.substring("http".length());
                String port = portalConfigService.getProperty(PortalConfigService.PORTAL_PORT);
                String securePort = portalConfigService.getProperty(PortalConfigService.PORTAL_SECURE_PORT);
                if(null != port && !"".equals(port) && null != securePort && !"".equals(securePort)){
                    realuri = realuri.replaceAll(port,securePort);
                }
            }
        } else {
            realuri = redirectURL;
        }

        //after login redirect (GPF-463 feature) to URI from session
        String redirectURI = (String) ((HttpServletRequest)req).getSession().getAttribute(SportletProperties.PORTAL_REDIRECT_PATH);
        if(null != redirectURI){
            realuri = realuri.substring(0,realuri.indexOf('/',8))+redirectURI;
        }

        //mark request as successfull login in order to invoke doAfterLogin (GPF-457 fix)
        req.setAttribute(SportletProperties.PORTAL_FILTER_EVENT, SportletProperties.PORTAL_FILTER_EVENT_AFTER_LOGIN);

        try {
            if (req.getParameter("ajax") != null) {
                //res.setContentType("text/html");
                //res.getWriter().print(realuri.toString());
            } else {
                res.sendRedirect(realuri.toString());
            }
        } catch (IOException e) {
            log.error("Unable to perform a redirect!", e);
        }
    }
	
	
	
	
	/*
	 *  login(PortletRequest request) reads the attributes from the HttpServletRequest.
	 *  If a user with the given username doesn't exists a new one is created.The
	 *  Shibboleth attributes are stored in the Database.
	 * 
	 */
	
    public User login(PortletRequest request) 
    	throws AuthenticationException, AuthorizationException {
		
		User accountRequest = null;
		ShibbolethUser accountRequestShib = null;
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		String username = (String) req.getAttribute("Shib-cn");
		String firstname = (String) req.getAttribute("Shib-givenName");
		String lastname = (String) req.getAttribute("Shib-surname");
		String email = (String) req.getAttribute("Shib-mail");
		String role = (String) req.getAttribute("Shib-role");
		String org = null;
		
			
		if ( (username == null) || username.equals("") ) 
			throw new IllegalArgumentException("Please provide a User Name");
		if ( (firstname == null) || firstname.equals("") ) 
			throw new IllegalArgumentException("Please provide a First Name");
		if ( (lastname == null) || lastname.equals("") ) 
			throw new IllegalArgumentException("Please provide a Last Name");
		if ( (email == null) || email.equals("") ) 
			throw new IllegalArgumentException("Please provide a E-Mail Address");
		if ( !email.contains("@") || !email.contains("."))
			throw new IllegalArgumentException("Please provide a valid E-Mail Address");
		
		if ( (role == null) || (role.length() < 1))
			role = "USER";
		else if ( !role.toLowerCase().equals("admin") )
			role = "USER";
				
		String passwd = "" + username.hashCode() + (new Date()).getTime();
		
		username = email;
			
		try {
			accountRequest = userManagerService.getUserByUserName(username);
			accountRequestShib = shibService.getShibUserByUserName(username);
			// if user doesn't exists, create a new one
			if ( accountRequest == null ) {
				log.debug("NEUEN BENUTZER ANLEGEN! \n");
				accountRequest = userManagerService.createUser();
				accountRequest.setUserName(username);
				accountRequest.setEmailAddress(email);
				log.debug("BENUTZER ANGELEGT! \n");
			}
			if ( accountRequestShib == null) {
				accountRequestShib = new ShibbolethUser();
				accountRequestShib.setID(accountRequest.getID());
				accountRequestShib.setShibUsername(username);
				accountRequestShib.setShibEmail(email);
			}
			
			accountRequest.setFirstName(firstname);
			accountRequestShib.setShibFirstname(firstname);
			accountRequest.setLastName(lastname);
			accountRequestShib.setShibLastname(lastname);
			accountRequest.setFullName(lastname + ", " + firstname);
			accountRequest.setOrganization(org);
			accountRequestShib.setShibCN(username);
			
			shibService.saveShibUser(accountRequestShib);
					
			PasswordEditor editor = passwordManagerService.editPassword(accountRequest);
			
			editor.setValue(passwd);
						
			passwordManagerService.savePassword(editor);
			
			
			userManagerService.saveUser(accountRequest);

			roleManagerService.addUserToRole(accountRequest, new PortletRole(role));
			if ( role.toLowerCase().equals("admin"))
				roleManagerService.addUserToRole(accountRequest, new PortletRole("USER"));
			
			
			request.setAttribute(SportletProperties.PORTLET_USER, accountRequest);
			
			request.getPortletSession(true).setAttribute(SportletProperties.PORTLET_USER, accountRequest.getID(), PortletSession.APPLICATION_SCOPE);
			request.setAttribute("shibboleth.login.redirect", "true");
			
			request.getPortletSession(true).setAttribute("shibboleth.login", "true", PortletSession.APPLICATION_SCOPE);
						
			req.getSession(true).setAttribute("shibboleth.user.somevalue", "true");
			
		} catch (Exception e) {
			log.error(this.getClass().getSimpleName()+".login(..) threw exception");
			e.printStackTrace();
		}
		
		return accountRequest;
	}
    
    
    protected String getLocalizedText(HttpServletRequest req, String key) {
        Locale locale = req.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
        return bundle.getString(key);
    }
    
    
}







