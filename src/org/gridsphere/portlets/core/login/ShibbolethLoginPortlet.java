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
		//boolean success = false;
		
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
	 * obtainSAMLAttributes(ActionFormEvent event) wird von "shibbolethLogin.jsp" aufgerufen. 
	 * Mit setNextState wird "obtainSAMLAttributes.jsp" aufgerufen
	 * 
	 */
	public void obtainSAMLAttributes(ActionFormEvent event) throws PortletException {
		log.debug("\n\n\n\n >>>>>>>>>>>>> in ShibbolethLoginPortlet : obtainSAMLAttributes("+event+") <<<<<<<<<<<<<<<<<<<<<<<<<<< \n\n\n");
				
		PortletRequest req = event.getActionRequest();
			
		setNextState(req, "login/obtainSAMLAttributes.jsp");
	
	}
	
	
	/*
	 *  gs_login(ActionFormEvent event) wird von ....  aufgerufen
	 *  Prueft ob in der PortletSession das Attribute "shibboleth.user.username" gesetzt ist (Wird in GridSphereFilter von
	 *  getSAMLAttributes gesetzt). 
	 *  Wenn ja, wird login(PortletRequest request) aufgerufen, sonst mit setNextState "loginFromShibboleth.jsp"
	 * 
	 */
	public void gs_login(ActionFormEvent event) throws PortletException {
		log.debug(">>>>>>>>>>>>>  in ShibbolethLoginPortlet : gs_login");
		ActionRequest req = event.getActionRequest();
		HttpServletRequest request = (HttpServletRequest) req;
		
		/*String shib_flag = (String) req.getPortletSession(true).getAttribute("shibboleth.user.username");
				
		log.debug(">>>>>>>>>>>>>>-------------<<<<<<<<<<<< in gs_login shibboleth.user.username = " + shib_flag);
		if ( shib_flag != null) {
			log.debug("\n\n\n\n >>>>>>>>>>>>>>>>>>>>>>> IM IF shibboleth.use.username != null <<<<<<<<<<<<<<<<<<<<<<< \n\n\n\n");
				
			try {
	            log.debug("##########>>>>>>>>>> in LoginPortlet gs_login Aufruf von login(ActionFormEvent event), event = " + event);
	        	login(event);
	        } catch (AuthorizationException err) {
	        	log.debug("shibboleth.user.somevalue = " + request.getSession(true).getAttribute("shibboleth.user.somevalue"));
	        	request.getSession(true).removeAttribute("shibboleth.user.somevalue");
	        	log.debug("shibboleth.user.somevalue = " + request.getSession(true).getAttribute("shibboleth.user.somevalue"));
	        	log.debug(err.getMessage());
	            req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
	            err.printStackTrace();
	        } catch (AuthenticationException err) {
	            log.debug(err.getMessage());
	            req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
	            err.printStackTrace();
	        }
		}	
		else {
			//req.setAttribute("shibboleth.login.redirect", "false");
			//setNextState(req, DEFAULT_VIEW_PAGE);
			setNextState(req, "login/shibbolethLogin.jsp");
			//setNextState(req, "login/loginFromShibboleth.jsp");
		}*/
		//req.getPortletSession(true).removeAttribute("shibboleth.account.disabled");
		
		try {
            log.debug("##########>>>>>>>>>> in LoginPortlet gs_login Aufruf von login(ActionFormEvent event), event = " + event);
        	login(event);
        } catch (AuthorizationException err) {
        	log.debug("shibboleth.user.somevalue = " + request.getSession(true).getAttribute("shibboleth.user.somevalue"));
        	// fuer disable account benoetigt
        	//request.getSession(true).removeAttribute("shibboleth.user.somevalue");
        	//log.debug("shibboleth.user.somevalue = " + request.getSession(true).getAttribute("shibboleth.user.somevalue"));
        	log.debug(err.getMessage());
            req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
        	// zum testen der Fehlermeldung-Ausgabe
        	//req.getPortletSession(true).setAttribute("shibboleth.account.disabled", "true");
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
    	
    	log.debug(">>>>>>>>>>>>>>>>>>>>> in ShibbolethLoginPortlet : login(ActionFormEvent)");
    	
        ActionRequest req = event.getActionRequest();
        ActionResponse res = event.getActionResponse();
        

        User user = null;
		user = login(req);
		
		
		log.debug(">>>>>>>>>>>>>>>>>>>>>>>>> Hole user von login(PortletRequest) User =" + user.getFullName());
        Long now = Calendar.getInstance().getTime().getTime();
        user.setLastLoginTime(now);
        Integer numLogins = user.getNumLogins();
        if (numLogins == null) numLogins = 0;
        numLogins++;

        user.setNumLogins(numLogins);
        log.debug(">>>>>>>>>>>>>>>>>>>>>> Setzt numLogins fuer User");
        user.setAttribute(PortalConfigService.LOGIN_NUMTRIES, "0");

        userManagerService.saveUser(user);
        log.debug(">>>>>>>>>>>>>>>>>>>>>> Speichere User");
        //req.setAttribute(SportletProperties.PORTLET_USER, user);
        //req.getPortletSession(true).setAttribute(SportletProperties.PORTLET_USER, user.getID(), PortletSession.APPLICATION_SCOPE);

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
	 *  login(PortletRequest request) holt die von Shibboleth gesendeten Attribute aus der PortletSession und ueberprueft sie.
	 *  Wenn noch kein user mit diesem username existiert wird er angelegt. Alle Attribute werden gespeichert.
	 *  SportletProperties.PORTLET_USER wird im PortletRequest und der Portlet Session gesetzt.
	 *  Im PortletRequest wird das Attributr "shibboleth.login.redirect" auf "true" gesetzt
	 *  In der Portlet Session wird das Attribute "shibboleth.login" auf "true" gesetzt
	 * 
	 */
	
    public User login(PortletRequest request) 
    	throws AuthenticationException, AuthorizationException {
		log.debug(">>>>>>>>>>>> in ShibbolethLoginPortlet : login(PortletRequest)");
		User accountRequest = null;
		ShibbolethUser accountRequestShib = null;
		
		HttpServletRequest req = (HttpServletRequest) request;
		//testen start
		/*String shib_cn = (String) req.getAttribute("Shib-cn");
    	String shib_surname = (String) req.getAttribute("Shib-surname");
    	String shib_givenname = (String) req.getAttribute("Shib-givenName");
    	String shib_ep_affiliation = (String) req.getAttribute("Shib-EP-Affiliation");
    	String shib_mail = (String) req.getAttribute("Shib-mail");
    	String shib_role = (String) req.getAttribute("Shib-role");
    	String test_var = (String) req.getAttribute("Test-Var");
    	log.debug("\n\n\n\nAttribute vom Apache");
    	log.debug("Shib-cn = " + shib_cn);
    	log.debug("Shib-surname = " + shib_surname);
    	log.debug("Shib-givenName = " + shib_givenname);
    	log.debug("Shib-EP-Affiliation = " + shib_ep_affiliation);
    	log.debug("Shib-mail = " + shib_mail);
    	log.debug("Shib-role = " + shib_role + "\n\n\n\n");
    	log.debug("Test-Var = " + test_var);*/
		//testen ende
		
		//log.debug("\n\nPortletRequest : shibboleth.user.attributes = " + request.getPortletSession().getAttribute("shibboleth.user.attributes") + "\n\n");
		//log.debug("\n\nPortletRequest getPortletSession(true): shibboleth.user.attributes = " + request.getPortletSession(true).getAttribute("shibboleth.user.attributes") + "\n\n");
		//request.getPortletSession(true).removeAttribute("shibboleth.user.attributes");
						
		/*String username = (String) request.getPortletSession(true).getAttribute("shibboleth.user.username");
		String firstname = (String) request.getPortletSession(true).getAttribute("shibboleth.user.givenname");
		String lastname = (String) request.getPortletSession(true).getAttribute("shibboleth.user.surname");
		String email = (String) request.getPortletSession(true).getAttribute("shibboleth.user.email");
		String org = (String) request.getPortletSession(true).getAttribute("shibboleth.user.organization");
		String role = (String) request.getPortletSession(true).getAttribute("shibboleth.user.role");
		String idp = (String) request.getPortletSession(true).getAttribute("shibboleth.user.idp");*/
		
		String username = (String) req.getAttribute("Shib-cn");
		String firstname = (String) req.getAttribute("Shib-givenName");
		String lastname = (String) req.getAttribute("Shib-surname");
		String email = (String) req.getAttribute("Shib-mail");
		String role = (String) req.getAttribute("Shib-role");
		String eppn = (String) req.getAttribute("Shib-eppn");
		String sessionID = (String) req.getAttribute("Shib-Session-ID");
		String identityProvider = (String) req.getAttribute("Shib-Identity-Provider");
		String epAffiliation = (String) req.getAttribute("Shib-EP-Affiliation");
		String epUnscopedAffiliation = (String) req.getAttribute("Shib-EP-UnscopedAffiliation");
		String samlAssertionURL = (String) req.getAttribute("Shib-Assertion-01");
		String samlAssertionURL2 = (String) req.getAttribute("Shib-Assertion-02");
		String samlAssertionCount = (String) req.getAttribute("Shib-Assertion-Count");
		String appId = (String) req.getAttribute("Shib-Application-ID");
		String authInstant = (String) req.getAttribute("Shib-Authentication-Instant");
		String authContext = (String) req.getAttribute("Shib-AuthnContext-Decl");
		String org = null;
		
		log.debug("\n\n\n\nAttribute vom Apache mod_jk");
    	log.debug("Shib-cn = " + username);
    	log.debug("Shib-givenName = " + firstname);
    	log.debug("Shib-surname = " + lastname);
    	log.debug("Shib-mail = " + email);
    	log.debug("Shib-role = " + role);
    	log.debug("Shib-eppn = " + eppn);
    	log.debug("Shib-Session-ID = " + sessionID);
    	log.debug("Shib-Identity-Provider = " + identityProvider);
    	log.debug("Shib-EP-Affiliation = " + epAffiliation);
    	log.debug("Shib-EP-UnscopedAffiliation = " + epUnscopedAffiliation);
    	log.debug("Shib-Assertion-01 = " + samlAssertionURL);
    	log.debug("Shib-Assertion-02 = " + samlAssertionURL2);
    	log.debug("Shib-Assertion-Count = " + samlAssertionCount);
    	log.debug("Shib-Application-Count = " + appId);
    	log.debug("Shib-Authentication-Instant = "+ authInstant);
    	log.debug("Shib-AuthnContext-Decl = " + authContext);
    		
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
		//role = "ADMIN";
		
		String passwd = "" + username.hashCode() + (new Date()).getTime();
		//String passwd = "123456";
		
		username = email;
		
		
		
		try {
			log.debug("\n\n\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FEHLERSUCHE ANFANG!!!! <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< \n\n\n\n");
			accountRequest = userManagerService.getUserByUserName(username);
			accountRequestShib = shibService.getShibUserByUserName(username);
			// wenn user nicht existiert
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
			log.debug("Neusetzen der Attribute firstname, lastname, fullname, organization.");
			log.debug("FirstName vor Neusetzen: "+accountRequest.getFirstName());
			accountRequest.setFirstName(firstname);
			accountRequestShib.setShibFirstname(firstname);
			log.debug("FirstName nach Neusetzen: "+accountRequest.getFirstName());
			log.debug("LastName vor Neusetzen: "+accountRequest.getLastName());
			accountRequest.setLastName(lastname);
			accountRequestShib.setShibLastname(lastname);
			log.debug("LastName nach Neusetzen: "+accountRequest.getLastName());
			log.debug("Fullname vor Neusetzen: "+accountRequest.getFullName());
			accountRequest.setFullName(lastname + ", " + firstname);
			log.debug("Fullname nach Neusetzen: "+accountRequest.getFullName());
			accountRequest.setOrganization(org);
			
			accountRequestShib.setShibAttr1(epAffiliation);
			accountRequestShib.setShibCN(username);
			
			shibService.saveShibUser(accountRequestShib);
			log.debug("\n\n\n\nShibboleth user = \n" + accountRequestShib);
			
			PasswordEditor editor = passwordManagerService.editPassword(accountRequest);
			log.debug("password: "+passwd);
			editor.setValue(passwd);
			
			
			passwordManagerService.savePassword(editor);
			
			log.debug("password: "+passwordManagerService.getPassword(accountRequest));
			
			log.debug("user object vor saveUser: "+accountRequest.getEmailAddress());
			
			userManagerService.saveUser(accountRequest);

			log.debug("user object nach saveUser u. getUserByName(): "+userManagerService.getUserByUserName(email));
			
			roleManagerService.addUserToRole(accountRequest, new PortletRole(role));
			if ( role.toLowerCase().equals("admin"))
				roleManagerService.addUserToRole(accountRequest, new PortletRole("USER"));
			
			
			request.setAttribute(SportletProperties.PORTLET_USER, accountRequest);
			log.debug(SportletProperties.PORTLET_USER+": "+request.getAttribute(SportletProperties.PORTLET_USER));
			request.getPortletSession(true).setAttribute(SportletProperties.PORTLET_USER, accountRequest.getID(), PortletSession.APPLICATION_SCOPE);
			log.debug(SportletProperties.PORTLET_USER+": "+request.getPortletSession(true).getAttribute(SportletProperties.PORTLET_USER));
			request.setAttribute("shibboleth.login.redirect", "true");
			log.debug("request.setAttribute('shibboleth.login.redirect', 'true'): "+request.getAttribute("shibboleth.login.redirect"));
			request.getPortletSession(true).setAttribute("shibboleth.login", "true", PortletSession.APPLICATION_SCOPE);
			log.debug("PortletSession request.setAttribute('shibboleth.login', 'true'): "+request.getPortletSession(true).getAttribute("shibboleth.login"));
			
			req.getSession(true).setAttribute("shibboleth.user.somevalue", "true");
			
		} catch (Exception e) {
			log.error(this.getClass().getSimpleName()+".login(..) threw exception blabala");
			//pm.endTransaction();
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







