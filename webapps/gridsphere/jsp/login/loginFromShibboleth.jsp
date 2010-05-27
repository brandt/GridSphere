<!--


-->

<%@ page import="org.gridsphere.portlets.util.*"%>


<%@ taglib uri="/portletUI" prefix="ui"%>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>

<portlet:defineObjects/>

<%
	String shib_flag = (String) request.getAttribute("shibboleth.login.redirect");
	
	
	if ((shib_flag != null) && (shib_flag.equals("true"))) {
		request.removeAttribute("shibboleth.login.redirect");
%>
	
		No login to GridSphere
<%
	} else {
%>
		The authentication in Shibboleth is no success!
<%
	}
%>

