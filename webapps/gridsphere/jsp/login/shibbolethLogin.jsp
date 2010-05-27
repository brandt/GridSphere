<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ taglib uri="/portletUI" prefix="ui" %>
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>

<portlet:defineObjects/>

<%
	String shib_flag = (String)request.getAttribute("shibboleth.login.enabled");
	
	if((shib_flag != null) && (shib_flag.equals("true")))
	{
		String account_disabled = (String)request.getAttribute("shibboleth.account.disabled");
		if((account_disabled != null) && (account_disabled.equals("true"))) 
		{
%>
			<ui:form>
				<ui:messagebox value="Account disabled by GridSphere Administrator"/>			
		    	<ui:actionsubmit cssStyle="margin-right: 30px;" action="obtainSAMLAttributes" key="Login from Shibboleth" />
			</ui:form>
<%
		}
		else
		{
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
