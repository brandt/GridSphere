/*
 * @author <a href="mailto:novotny@gridsphere.org">Jason Novotny</a>
 * @version $Id$
 */
package org.gridsphere.portlets.core.login;

import org.gridsphere.layout.PortletPageFactory;
import org.gridsphere.portlet.impl.PortletURLImpl;
import org.gridsphere.portlet.impl.SportletProperties;
import org.gridsphere.portlet.service.PortletServiceException;
import org.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridsphere.provider.event.jsr.FormEvent;
import org.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridsphere.provider.portletui.beans.HiddenFieldBean;
import org.gridsphere.provider.portletui.beans.PasswordBean;
import org.gridsphere.provider.portletui.beans.TextFieldBean;
import org.gridsphere.services.core.filter.PortalFilter;
import org.gridsphere.services.core.filter.PortalFilterService;
import org.gridsphere.services.core.mail.MailMessage;
import org.gridsphere.services.core.mail.MailService;
import org.gridsphere.services.core.portal.PortalConfigService;
import org.gridsphere.services.core.request.Request;
import org.gridsphere.services.core.request.RequestService;
import org.gridsphere.services.core.security.auth.AuthModuleService;
import org.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridsphere.services.core.security.auth.AuthorizationException;
import org.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridsphere.services.core.security.auth.modules.LateUserRetrievalAuthModule;
import org.gridsphere.services.core.security.auth.modules.impl.UserDescriptor;
import org.gridsphere.services.core.security.auth.modules.impl.AuthenticationParameters;
import org.gridsphere.services.core.security.password.PasswordEditor;
import org.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridsphere.services.core.user.User;
import org.gridsphere.services.core.user.UserManagerService;

import javax.portlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.*;

public class LoginPortlet extends ActionPortlet {

	private static String FORGOT_PASSWORD_LABEL = "forgotpassword";

	private static long REQUEST_LIFETIME = 1000 * 60 * 60 * 24 * 3; // 3 days

	public static final String LOGIN_ERROR_FLAG = "LOGIN_FAILED";
	public static final Integer LOGIN_ERROR_UNKNOWN = new Integer(-1);

	public static final String DO_VIEW_USER_EDIT_LOGIN = "login/createaccount.jsp"; //edit user
	public static final String DO_FORGOT_PASSWORD = "login/forgotpassword.jsp";
	public static final String DO_NEW_PASSWORD = "login/newpassword.jsp";

	private UserManagerService userManagerService = null;

	private PortalConfigService portalConfigService = null;
	private RequestService requestService = null;
	private MailService mailService = null;
	private AuthModuleService authModuleService = null;
	private PasswordManagerService passwordManagerService = null;
	
//	removed by Bastian Boegel, University of Ulm, 2009
//	private String newpasswordURL = null;
//	private String redirectURL = null;


	public void init(PortletConfig config) throws PortletException {
		super.init(config);
		userManagerService = (UserManagerService) createPortletService(UserManagerService.class);
		requestService = (RequestService) createPortletService(RequestService.class);
		mailService = (MailService) createPortletService(MailService.class);
		portalConfigService = (PortalConfigService) createPortletService(PortalConfigService.class);
		authModuleService = (AuthModuleService) createPortletService(AuthModuleService.class);
		passwordManagerService = (PasswordManagerService) createPortletService(PasswordManagerService.class);
		DEFAULT_VIEW_PAGE = "doViewUser";
	}

	public void doViewUser(RenderFormEvent event) throws PortletException {
		doViewUser(event.getRenderRequest(), event);
	} // public void doViewUser(RenderFormEvent event) throws PortletException
		
	public void doViewUser(ActionFormEvent event) throws PortletException {
		doViewUser(event.getActionRequest(), event);
	} // public void doViewUser(RenderFormEvent event) throws PortletException
	
	private void doViewUser(PortletRequest request, FormEvent event) {
		log.debug("in LoginPortlet: doViewUser");

/* removed by Bastian Boegel, University of Ulm, 2009
		if (newpasswordURL == null) {
			PortletURL url = response.createActionURL();
			((PortletURLImpl) url).setAction("newpassword");
			((PortletURLImpl) url).setLayout("login");
			((PortletURLImpl) url).setEncoding(false);
			newpasswordURL = url.toString();
		}


		if (redirectURL == null) {
			PortletURL url = response.createRenderURL();
			((PortletURLImpl) url).setLayout(PortletPageFactory.USER_PAGE);
			((PortletURLImpl) url).setEncoding(false);
			redirectURL = url.toString();
		}
*/
		PasswordBean pass = event.getPasswordBean("password");
		pass.setValue("");

		// Check certificates
		String x509supported = portalConfigService.getProperty(PortalConfigService.SUPPORT_X509_AUTH);
		if ((x509supported != null) && (x509supported.equalsIgnoreCase("true"))) {
			X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
			if (certs != null && certs.length > 0) {
				request.setAttribute("certificate", certs[0].getSubjectDN().toString());
			}
		}

		String remUser = portalConfigService.getProperty(PortalConfigService.REMEMBER_USER);
		if ((remUser != null) && (remUser.equalsIgnoreCase("TRUE"))) {
			request.setAttribute("remUser", "true");
		}

		Boolean useSecureLogin = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.USE_HTTPS_LOGIN));

		request.setAttribute("useSecureLogin", useSecureLogin.toString());
		boolean canUserCreateAccount = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.CAN_USER_CREATE_ACCOUNT)).booleanValue();
		if (canUserCreateAccount) request.setAttribute("canUserCreateAcct", "true");
		boolean dispUser = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.SEND_USER_FORGET_PASSWORD)).booleanValue();
		if (dispUser) request.setAttribute("dispPass", "true");

		String errorMsg = (String) request.getPortletSession(true).getAttribute(LOGIN_ERROR_FLAG);

		if (errorMsg != null) {
			createErrorMessage(event, errorMsg);
			request.getPortletSession(true).removeAttribute(LOGIN_ERROR_FLAG);
		}

		Boolean useUserName = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.USE_USERNAME_FOR_LOGIN));
		if (useUserName) request.setAttribute("useUserName", "true");

		setNextState(request, "login/login.jsp");
	}
	
	public void gs_login(ActionFormEvent event) throws PortletException {
		log.debug("in LoginPortlet: gs_login");
		PortletRequest req = event.getActionRequest();

		try {
			login(event);
		} catch (AuthorizationException err) {
			log.debug(err.getMessage());
			req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
		} catch (AuthenticationException err) {
			log.debug(err.getMessage());
			req.getPortletSession(true).setAttribute(LOGIN_ERROR_FLAG, err.getMessage());
		}

		setNextState(req, DEFAULT_VIEW_PAGE);
	}


	public void notifyUser(ActionFormEvent evt) {
		PortletRequest req = evt.getActionRequest();

		User user;
		TextFieldBean emailTF = evt.getTextFieldBean("emailTF");

		if (emailTF.getValue().equals("")) {
			createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_NO_EMAIL"));
			return;
		} else {
			user = userManagerService.getUserByEmail(emailTF.getValue());
		}
		if (user == null) {
			createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_NOEXIST"));
			return;
		}

		// create a request
		Request request = requestService.createRequest(FORGOT_PASSWORD_LABEL);
		long now = Calendar.getInstance().getTime().getTime();

		request.setLifetime(new Date(now + REQUEST_LIFETIME));
		request.setUserID(user.getID());
		requestService.saveRequest(request);

		MailMessage mailToUser = new MailMessage();
		mailToUser.setEmailAddress(emailTF.getValue());
		String subjectHeader = portalConfigService.getProperty("LOGIN_FORGOT_SUBJECT");
		if (subjectHeader == null) subjectHeader = getLocalizedText(req, "MAIL_SUBJECT_HEADER");
		mailToUser.setSubject(subjectHeader);
		StringBuffer body = new StringBuffer();

		String forgotMail = portalConfigService.getProperty("LOGIN_FORGOT_BODY");
		if (forgotMail == null) forgotMail = getLocalizedText(req, "LOGIN_FORGOT_MAIL");
		body.append(forgotMail).append("\n\n");
		body.append(getLocalizedText(req, "USERNAME")).append(" :").append(user.getUserName()).append("\n\n");

        PortletURLImpl portletURL = new PortletURLImpl((HttpServletRequest) req, (HttpServletResponse) evt.getActionResponse(), false);
        portletURL.setAction("");
        portletURL.setAction("newpassword");
        portletURL.setLayout("login");
        portletURL.setEncoding(false);
		String newPasswordURL = portletURL.toString();

		
		body.append(newPasswordURL).append("&reqid=").append(request.getOid());
		mailToUser.setBody(body.toString());
		mailToUser.setSender(portalConfigService.getProperty(PortalConfigService.MAIL_FROM));
		try {
			mailService.sendMail(mailToUser);
			createSuccessMessage(evt, this.getLocalizedText(req, "LOGIN_SUCCESS_MAIL"));
		} catch (PortletServiceException e) {
			log.error("Unable to send mail message!", e);
			createErrorMessage(evt, this.getLocalizedText(req, "LOGIN_FAILURE_MAIL"));
			setNextState(req, DEFAULT_VIEW_PAGE);
		}
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

		User user = login(req);
		Long now = Calendar.getInstance().getTime().getTime();
		user.setLastLoginTime(now);
		Integer numLogins = user.getNumLogins();
		if (numLogins == null) numLogins = 0;
		numLogins++;

		user.setNumLogins(numLogins);
		user.setAttribute(PortalConfigService.LOGIN_NUMTRIES, "0");

		userManagerService.saveUser(user);

		req.setAttribute(SportletProperties.PORTLET_USER, user);
		req.getPortletSession(true).setAttribute(SportletProperties.PORTLET_USER, user.getID(), PortletSession.APPLICATION_SCOPE);

		String query = event.getAction().getParameter("queryString");

		// inserted by Bastian Boegel, University of Ulm, 2009
		PortletURLImpl url = new PortletURLImpl((HttpServletRequest) req, (HttpServletResponse) res, true);
		((PortletURLImpl) url).setLayout(PortletPageFactory.USER_PAGE);
		((PortletURLImpl) url).setEncoding(false);
		String redirectURL = url.toString();


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

		log.debug("in login redirecting to portal: " + realuri.toString());
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


	public User login(PortletRequest req)
	throws AuthenticationException, AuthorizationException {
		boolean lateUserRetrieval = false;
		boolean hasLateUserRetrievalAuthModules = false;

		String loginName = req.getParameter("username");
		String loginPassword = req.getParameter("password");
		String certificate = null;

		X509Certificate[] certs = (X509Certificate[]) req.getAttribute("javax.servlet.request.X509Certificate");
		if (certs != null && certs.length > 0) {
			certificate = certificateTransform(certs[0].getSubjectDN().toString());
		}

		User user = null;

		// if using client certificate, then don't use login modules
		if (certificate == null) {
			if ((loginName == null) || (loginPassword == null)) {
				throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_BLANK"));
			}
			// first get user
			Boolean useUserName = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.USE_USERNAME_FOR_LOGIN));
			if (useUserName) {

				user = userManagerService.getUserByUserName(loginName);
			} else {
				user = userManagerService.getUserByEmail(loginName);
			}

			// check if there are late user retrieval modules in case user is not obtained from the active login module
			List<LoginAuthModule> modules = authModuleService.getActiveAuthModules();
			Iterator modulesIterator = modules.iterator();
			while (modulesIterator.hasNext()) {
				if (modulesIterator.next() instanceof LateUserRetrievalAuthModule) {
					hasLateUserRetrievalAuthModules = true;
					break;
				}
			}

			if (null == user && hasLateUserRetrievalAuthModules)
				lateUserRetrieval = true;
		} else {

			log.debug("Using certificate for login :" + certificate);
			List userList = userManagerService.getUsersByAttribute("certificate", certificate, null);
			if (!userList.isEmpty()) {
				user = (User) userList.get(0);
			}
		}

		int numTriesInt = 1;
		if (!lateUserRetrieval) {
			if (user == null) throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_NOUSER"));

			// tried one to many times using same name
			int defaultNumTries = Integer.valueOf(portalConfigService.getProperty(PortalConfigService.LOGIN_NUMTRIES)).intValue();
			String numTries = (String) user.getAttribute(PortalConfigService.LOGIN_NUMTRIES);
			if (numTries != null)
				numTriesInt = Integer.valueOf(numTries).intValue();

			System.err.println("num tries = " + numTriesInt);
			if ((defaultNumTries != -1) && (numTriesInt >= defaultNumTries)) {
				disableAccount(req);
				throw new AuthorizationException(getLocalizedText(req, "LOGIN_TOOMANY_ATTEMPTS"));
			}

			String accountStatus = (String) user.getAttribute(User.DISABLED);
			if ((accountStatus != null) && ("TRUE".equalsIgnoreCase(accountStatus)))
				throw new AuthorizationException(getLocalizedText(req, "LOGIN_AUTH_DISABLED"));
		}

		// If authorized via certificates no other authorization needed
		if (certificate != null) return user;

		// second invoke the appropriate auth module
		List<LoginAuthModule> modules = authModuleService.getActiveAuthModules();

		Collections.sort(modules);
		AuthenticationException authEx = null;

		Map parametersMap = new HashMap();
		if(hasLateUserRetrievalAuthModules){
			Enumeration parametersNamesEnumeration = req.getParameterNames();
			while (parametersNamesEnumeration.hasMoreElements()) {
				String parameterName = (String) parametersNamesEnumeration.nextElement();
				parametersMap.put(parameterName, req.getParameter(parameterName));
			}
		}

		Iterator it = modules.iterator();
		if (lateUserRetrieval)
			log.debug("in login: Use late user retrieval modules only");
		log.debug("in login: Active modules are: ");
		boolean success = false;
		while (it.hasNext()) {
			success = false;
			LoginAuthModule mod = (LoginAuthModule) it.next();
			//in case of late user retrieval use LateUserRetrievalAuthModule modules only
			if (lateUserRetrieval && !(mod instanceof LateUserRetrievalAuthModule)) {
				log.debug(mod.getModuleName() + " (NOT late user retrieval module)");
				continue;
			}
			log.debug(mod.getModuleName());
			try {
				if (mod instanceof LateUserRetrievalAuthModule) {
					UserDescriptor userDescriptor = ((LateUserRetrievalAuthModule) mod).checkAuthentication(new AuthenticationParameters(loginName, loginPassword, parametersMap, req));
					//TODO: substitute with localized messages
					if(null == userDescriptor)
						throw new AuthenticationException("Late user retrieval module did not return user descriptor");
					//TODO: substitute with localized messages
					if(null == userDescriptor.getUserName() && null == userDescriptor.getEmailAddress() && null == userDescriptor.getID())
						throw new AuthenticationException("Late user retrieval module did not return user descriptor containing login name or email or id");

					User tmpUser = null;
					//obtain user by user name or email or id
					if(null != userDescriptor.getUserName())
						tmpUser = userManagerService.getUserByUserName(userDescriptor.getUserName());
					else if(null != userDescriptor.getEmailAddress())
						tmpUser = userManagerService.getUserByEmail(userDescriptor.getEmailAddress());
					else if(null != userDescriptor.getID()) {
						List users = userManagerService.getUsers();
						for (int i = 0; i < users.size(); i++) {
							User user1 = (User) users.get(i);
							if(user1.getID().equals(userDescriptor.getID())){
								tmpUser = user1;
								break;
							}
						}
					}
					//TODO: substitute with localized messages
					if(null == tmpUser)
						throw new AuthenticationException("User descriptor returned by late user retrieval is invalid");

					//check if user descriptor matches user object

					//TODO: substitute with localized messages
					if(null != userDescriptor.getID() && !tmpUser.getID().equals(userDescriptor.getID()))
						throw new AuthenticationException("ID in auth module and GridSphere doesn't match");

					//TODO: substitute with localized messages
					if(null != userDescriptor.getEmailAddress() && !tmpUser.getEmailAddress().equals(userDescriptor.getEmailAddress()))
						throw new AuthenticationException("User email in auth module and GridSphere doesn't match");

					//TODO: substitute with localized messages
					if(null != userDescriptor.getUserName() && !tmpUser.getUserName().equals(userDescriptor.getUserName()))
						throw new AuthenticationException("User name in auth module and GridSphere doesn't match");

					user = tmpUser;
				} else {
					mod.checkAuthentication(user, loginPassword);
				}
				success = true;
			} catch (AuthenticationException e) {
				//TODO: shouldn't we accumulate authentication error messages from all modules - not from the last only ?
				String errMsg = mod.getModuleError(e.getMessage(), req.getLocale());
				if (errMsg != null) {
					authEx = new AuthenticationException(errMsg);
				} else {
					authEx = e;
				}
			} catch (Exception e){
				log.error("",e);
			}
			if (success) break;
		}
		if (!lateUserRetrieval && !success) {

			numTriesInt++;
			user.setAttribute(PortalConfigService.LOGIN_NUMTRIES, String.valueOf(numTriesInt));
			userManagerService.saveUser(user);
		}
		if (!success)
			throw authEx;

		return user;
	}

	/**
	 * Transform certificate subject from :
	 * CN=Engbert Heupers, O=sara, O=users, O=dutchgrid
	 * to :
	 * /O=dutchgrid/O=users/O=sara/CN=Engbert Heupers
	 *
	 * @param certificate string
	 * @return certificate string
	 */
	private String certificateTransform(String certificate) {
		String ls[] = certificate.split(", ");
		StringBuffer res = new StringBuffer();
		for (int i = ls.length - 1; i >= 0; i--) {
			res.append("/");
			res.append(ls[i]);
		}
		return res.toString();
	}

	protected String getLocalizedText(HttpServletRequest req, String key) {
		Locale locale = req.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("gridsphere.resources.Portlet", locale);
		return bundle.getString(key);
	}

	public void disableAccount(PortletRequest req) {
		//PortletRequest req = event.getRenderRequest();
		String loginName = req.getParameter("username");
		User user = userManagerService.getUserByUserName(loginName);
		if (user != null) {
			user.setAttribute(User.DISABLED, "true");
			userManagerService.saveUser(user);

			MailMessage mailToUser = new MailMessage();
			StringBuffer body = new StringBuffer();
			body.append(getLocalizedText(req, "LOGIN_DISABLED_MSG1")).append(" ").append(getLocalizedText(req, "LOGIN_DISABLED_MSG2")).append("\n\n");
			mailToUser.setBody(body.toString());
			mailToUser.setSubject(getLocalizedText(req, "LOGIN_DISABLED_SUBJECT"));
			mailToUser.setEmailAddress(user.getEmailAddress());

			MailMessage mailToAdmin = new MailMessage();
			StringBuffer body2 = new StringBuffer();
			body2.append(getLocalizedText(req, "LOGIN_DISABLED_ADMIN_MSG")).append(" ").append(user.getUserName());
			mailToAdmin.setBody(body2.toString());
			mailToAdmin.setSubject(getLocalizedText(req, "LOGIN_DISABLED_SUBJECT") + " " + user.getUserName());
			String portalAdminEmail = portalConfigService.getProperty(PortalConfigService.PORTAL_ADMIN_EMAIL);
			mailToAdmin.setEmailAddress(portalAdminEmail);

			try {
				mailService.sendMail(mailToUser);
				mailService.sendMail(mailToAdmin);
			} catch (PortletServiceException e) {
				log.error("Unable to send mail message!", e);
				//createErrorMessage(event, this.getLocalizedText(req, "LOGIN_FAILURE_MAIL"));
			}
		}
	}

	public void displayForgotPassword(RenderFormEvent event) {
		boolean sendMail = Boolean.valueOf(portalConfigService.getProperty(PortalConfigService.SEND_USER_FORGET_PASSWORD)).booleanValue();
		if (sendMail) {
			PortletRequest req = event.getRenderRequest();
			setNextState(req, DO_FORGOT_PASSWORD);
		}
	}


	public void newpassword(ActionFormEvent evt) {
		PortletRequest req = evt.getActionRequest();
		String id = req.getParameter("reqid");
		Request request = requestService.getRequest(id, FORGOT_PASSWORD_LABEL);
		if (request != null) {
			HiddenFieldBean reqid = evt.getHiddenFieldBean("reqid");
			reqid.setValue(id);
			setNextState(req, DO_NEW_PASSWORD);
		} else {
			setNextState(req, DEFAULT_VIEW_PAGE);
		}
	}

	public void doSavePass(ActionFormEvent event) {

		PortletRequest req = event.getActionRequest();

		HiddenFieldBean reqid = event.getHiddenFieldBean("reqid");
		String id = reqid.getValue();
		Request request = requestService.getRequest(id, FORGOT_PASSWORD_LABEL);
		if (request != null) {
			String uid = request.getUserID();
			User user = userManagerService.getUser(uid);
			passwordManagerService.editPassword(user);
			String passwordValue = event.getPasswordBean("password").getValue();
			String confirmPasswordValue = event.getPasswordBean("confirmPassword").getValue();

			if (passwordValue == null) {
				createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_NOTSET"));
				setNextState(req, DO_NEW_PASSWORD);
				return;
			}

			// Otherwise, password must match confirmation
			if (!passwordValue.equals(confirmPasswordValue)) {
				createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_MISMATCH"));
				setNextState(req, DO_NEW_PASSWORD);
				// If they do match, then validate password with our service
			} else {
				if (passwordValue.length() == 0) {
					createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_BLANK"));
					setNextState(req, DO_NEW_PASSWORD);
				} else if (passwordValue.length() < 5) {
					System.err.println("length < 5 password= " + passwordValue);
					createErrorMessage(event, this.getLocalizedText(req, "USER_PASSWORD_TOOSHORT"));
					setNextState(req, DO_NEW_PASSWORD);
				} else {
					// save password
					//System.err.println("saving password= " + passwordValue);
					PasswordEditor editPasswd = passwordManagerService.editPassword(user);
					editPasswd.setValue(passwordValue);
					editPasswd.setDateLastModified(Calendar.getInstance().getTime());
					passwordManagerService.savePassword(editPasswd);
					createSuccessMessage(event, this.getLocalizedText(req, "USER_PASSWORD_SUCCESS"));
					requestService.deleteRequest(request);
				}
			}
		}
	}

}
