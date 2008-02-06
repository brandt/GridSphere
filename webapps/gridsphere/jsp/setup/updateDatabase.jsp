<%@ page import="java.util.ResourceBundle" %>
<%@ page import="java.util.Locale" %>
<%!
    protected String getLocalizedText(String key, HttpServletRequest request) {
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

<div style="padding-top: 3px; margin: 0px 0px 0px 10px; padding-left: 8px; padding-right:
 5px; padding-bottom: 1px;">

    <h1><%= getLocalizedText("SETUP_DB_UPDATE_SETUP_GRIDSPHERE",request) %></h1>

    <% String errMsg = (String)request.getAttribute("error");
       if (errMsg != null) { %>

    <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>
           
    <%   } %>

    <%= getLocalizedText("SETUP_DB_UPDATE_DESC",request) %>
    <p/>

        <form method="POST" action="<%= request.getContextPath() %>/setup?install=update">


            <input type="submit" value="<%= getLocalizedText("SETUP_DB_UPDATE_BUTTON",request) %>"/>

        </form>

</div>
