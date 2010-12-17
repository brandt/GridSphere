<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<%
	String shib_flag = (String)request.getAttribute("shibboleth.login.enabled");
	String shib_SP = (String)request.getAttribute("shibboleth.sp");
	if((shib_flag != null) && (shib_flag.equals("true")))
	{
		if (shib_SP.equals("")) {
%>
			<ui:form>
				<ui:messagebox value="No Service Provider available"/>
			</ui:form>
<%
		} else {
%>
			<ui:form>
				<ui:actionsubmit cssStyle="margin-right: 30px;" action="obtainSAMLAttributes" key="Login from Shibboleth" />
			</ui:form>
<%
		}
	}
	else
	{
%>
			<ui:form>
				<ui:messagebox value="Login from Shibboleth is not allowed by GridSphere administrator"/>
			</ui:form>
<%

	}
%>
