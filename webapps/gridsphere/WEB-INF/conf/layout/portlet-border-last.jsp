<%@ page import="org.gridlab.gridsphere.layout.PortletStateLink,
                 org.gridlab.gridsphere.layout.LayoutProperties,
                 java.util.List,
                 java.util.Iterator"%>

 <% List stateLinks = (List)request.getAttribute(LayoutProperties.WINDOWSTATELINKS); %>
 <% Iterator statesIt = stateLinks.iterator(); %>
 <% PortletStateLink state; %>

  <% String font = (String)request.getAttribute("font"); %>
  <% String title = (String)request.getAttribute("title"); %>
  <% String titlecolor = (String)request.getAttribute("titlecolor"); %>

              <font color="#FFFFFF" face="<%= font %>">
              &nbsp;<%= title %>
              </font></td>
             <td>
<% while (statesIt.hasNext()) {
	state = (PortletStateLink)statesIt.next(); %>
		<a href="<%= state.getStateHref() %>"><img src="<%= state.getImageSrc() %>" align=right ></a>
<% } %>
             </td>
          </tr>
        </table>



