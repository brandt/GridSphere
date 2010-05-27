<!--
  * @author <a href="alin@melcoe.mq.edu.au">Aizhong Lin</a>
  * @version v 1.0 24/04/2007 13:44:55
-->

<%@ page import="org.gridsphere.portlets.util.*"%>

<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<% 
	javax.servlet.http.HttpSession hs = request.getSession(true);
	String shib_action = request.getParameter("shib_action");
	
	
	if((shib_action == null) || (shib_action.length() < 1))
	{
		shib_action = "nop";
	}
%>

	<SCRIPT language="JavaScript">
	<!--

		window.location="https://<%=ShibbolethProperties.getInstance().getProperty("host.dns")%>/gridsphere/gridsphere/login/shib_login?gs_action=gs_login&shib_action=<%=shib_action%>";

	//-->
	</SCRIPT>

