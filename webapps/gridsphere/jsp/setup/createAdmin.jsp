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

<div style="padding-top: 3px; padding-left: 8px; padding-right: 5px; margin-left: 10px;">

    <h1><%= getLocalizedText("SETUP_CRATE_ADMIN_SETUP_GRIDSPHERE",request) %></h1>


    <h2><%= getLocalizedText("SETUP_CRATE_ADMIN_TITLE",request) %></h2>

<%= getLocalizedText("SETUP_CRATE_ADMIN_DESC",request) %>

    <p/>

    <% String errMsg = (String)request.getAttribute("error");
           if (errMsg != null) { %>

        <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>

        <%   } %>

    <p/>

<form action="<%= request.getContextPath() %>/setup?install=admin" method="POST">

   <table>
       <tr>
           <td align="right">
               <label for="username"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_USER_NAME",request) %>:</label>
           </td>
           <td align="left">
                <input type="text" name="username" id="username">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="firstname"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_FIRST_NAME",request) %>:</label>
           </td>
           <td align="left">
                <input type="text" name="firstname" id="firstname">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="lastname"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_LAST_NAME",request) %>:</label>
           </td>
           <td align="left">
                <input type="text" name="lastname" id="lastname">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="email"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_EMAIL",request) %>:</label>
           </td>
           <td align="left">
                <input type="text" name="email" id="email">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="organization"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_ORGANIZATION",request) %>:</label>
           </td>
           <td align="left">
                <input type="text" name="organization" id="organization">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="password"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_PASSWORD",request) %>:</label>
           </td>
           <td align="left">
                <input type="password" name="password" id="password">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="password2"><%= getLocalizedText("SETUP_CRATE_ADMIN_FIELD_CONFIRM_PASSWORD",request) %>:</label>
           </td>
           <td align="left">
                <input type="password" name="password2" id="password2">
           </td>
       </tr>

   </table>

  <p/>

    <input type="submit" value="<%= getLocalizedText("SETUP_CRATE_ADMIN_BUTTON_CREATE_ACCOUNT",request) %>"/>

</form>
</div>