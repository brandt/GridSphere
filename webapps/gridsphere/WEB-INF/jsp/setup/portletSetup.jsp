<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>

<div style="padding-top: 3px; padding-left: 8px; padding-right: 5px; margin-left: 10px;">
    <h1>Portlet Setup</h1>

    <h2><%= request.getAttribute(SportletProperties.PORTLET_SETUP_TITLE) %></h2>

    <p><%= request.getAttribute(SportletProperties.PORTLET_SETUP_DESCRIPTION) %></p>

    <% String errMsg = (String) request.getAttribute("error");
        if (errMsg != null) {
    %>
    <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>
    <%
        }
    %>

    <form action="<%= request.getContextPath() %>/setup?install=portlet&<%= SportletProperties.PORTLET_SETUP_TYPE %>=<%= request.getAttribute(SportletProperties.PORTLET_SETUP_TYPE) %>"
          method="POST">
        <%
            out.flush();
            try {
                getServletContext().getContext("/" + request.getAttribute(SportletProperties.PORTLET_SETUP_PAGE_CONTEXT)).getRequestDispatcher((String) request.getAttribute(SportletProperties.PORTLET_SETUP_PAGE_INCLUDE)).include(request, response);
            } catch (javax.servlet.ServletException e) {
                out.println(e.getMessage());
            }
            out.flush();
        %>
        <hr/>
        <table>
            <tr>
                <td align="center">
                    <input type="submit" name="<%= SportletProperties.PORTLET_SETUP_OPERATION %>" value="Set"/>
                </td>
                <td align="center">
                    <input type="submit" name="<%= SportletProperties.PORTLET_SETUP_OPERATION %>" value="Skip module"/>
                </td>
                <td align="center">
                    <input type="submit" name="<%= SportletProperties.PORTLET_SETUP_OPERATION %>"
                           value="Skip <%= request.getAttribute(SportletProperties.PORTLET_SETUP_TYPE).equals(SportletProperties.PORTLET_SETUP_TYPE_PRE)?"pre":"post" %> portlet initialization setup"/>
                </td>
            </tr>
        </table>
    </form>
</div>