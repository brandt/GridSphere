<%@ page import="java.util.List,
                 java.util.Iterator,
                 org.gridlab.gridsphere.layout.PortletModeLink,
                 org.gridlab.gridsphere.layout.LayoutProperties,
                 org.gridlab.gridsphere.layout.PortletStateLink"%>

  <% String title = (String)request.getAttribute(LayoutProperties.TITLE); %>
  <% String titlecolor = (String)request.getAttribute(LayoutProperties.TITLECOLOR); %>
  <% String linecolor = (String)request.getAttribute(LayoutProperties.LINECOLOR); %>
  <% String thickness = (String)request.getAttribute(LayoutProperties.THICKNESS); %>
  <% List modeLinks = (List)request.getAttribute(LayoutProperties.PORTLETMODELINKS); %>
  <% Iterator modesIt = modeLinks.iterator(); %>
  <% PortletModeLink mode; %>

        <table width="100%" border="0" cellspacing="1" cellpadding="1" bgcolor="<%= linecolor %>">
          <tr>
            <td>
<% while (modesIt.hasNext()) {
	mode = (PortletModeLink)modesIt.next(); %>
		<a href="<%= mode.getModeHref() %>"><img src="<%= mode.getImageSrc() %>" ></a>
<% } %>
	        </td>
            <td height="20" align="left" valign="middle">

