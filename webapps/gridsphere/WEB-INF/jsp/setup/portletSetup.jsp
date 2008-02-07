<%@ page import="org.gridsphere.portlet.impl.SportletProperties" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%!
    protected String getLocalizedText(String key,HttpServletRequest request)
    {
        Locale locale = request.getLocale();
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("Portlet", locale);
            return bundle.getString(key);
        } catch (Exception e) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle("Portlet", Locale.ENGLISH);
                return bundle.getString(key);
            } catch (Exception ex) {
                return key;
            }
        }
    }
%>
<div style="padding-top: 3px; padding-left: 8px; padding-right: 5px; margin-left: 10px;">
    <h1><%= getLocalizedText("PORTLET_SETUP",request) %>&nbsp;[<%= request.getAttribute(SportletProperties.PORTLET_SETUP_MODULE_NUMBER) %> <%= getLocalizedText("PORTLET_SETUP_OF",request) %> <%= request.getAttribute(SportletProperties.PORTLET_SETUP_NUMBER_OF_MODULES) %>]</h1>
    <form action="<%= request.getContextPath() %>/setup?install=portlet&<%= SportletProperties.PORTLET_SETUP_TYPE %>=<%= request.getAttribute(SportletProperties.PORTLET_SETUP_TYPE) %>"
          method="POST">

        <fieldset>
            <legend><%= request.getAttribute(SportletProperties.PORTLET_SETUP_TITLE) %> (<%= request.getAttribute(SportletProperties.PORTLET_SETUP_PAGE_CONTEXT) %>)</legend>
            <%= request.getAttribute(SportletProperties.PORTLET_SETUP_DESCRIPTION) %>
            <p/>

            <% String errMsg = (String) request.getAttribute("error");
                if (errMsg != null) {
            %>
            <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>
            <%
                }
                out.flush();
                try {
                    getServletContext().getContext("/" + request.getAttribute(SportletProperties.PORTLET_SETUP_PAGE_CONTEXT)).getRequestDispatcher((String) request.getAttribute(SportletProperties.PORTLET_SETUP_PAGE_INCLUDE)).include(request, response);
                } catch (javax.servlet.ServletException e) {
                    out.println(e.getMessage());
                }
                out.flush();
            %>
            <hr/>
            <table width="100%" cellspacing="15">
                <tr>
                    <td align="center">
                        <input type="submit" name="<%= SportletProperties.PORTLET_SETUP_OPERATION %>=set" value="<%= getLocalizedText("PORTLET_SETUP_SET",request) %>"/>&nbsp;&nbsp;&nbsp;
                    </td>
                    <td align="center">
                        <input type="submit" name="<%= SportletProperties.PORTLET_SETUP_OPERATION %>=skip_module" value="<%= getLocalizedText("PORTLET_SETUP_SKIP_MODULE",request) %>"/>&nbsp;&nbsp;&nbsp;
                    </td>
                    <td align="center">
                        <input type="submit" name="<%= SportletProperties.PORTLET_SETUP_OPERATION %>=skip_pre_or_post_setup" value="<%= getLocalizedText("PORTLET_SETUP_SKIP_"+(request.getAttribute(SportletProperties.PORTLET_SETUP_TYPE).equals(SportletProperties.PORTLET_SETUP_TYPE_PRE)?"PRE":"POST")+"_SETUP",request) %>"/>
                    </td>
                </tr>
            </table>
        </fieldset>
        
    </form>
</div>