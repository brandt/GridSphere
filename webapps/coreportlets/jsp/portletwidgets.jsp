
<%@ taglib uri="/portletWidgets" prefix="gs" %>
<%@ taglib uri="/portletAPI" prefix="portletAPI" %>

<portletAPI:init/>

<html bgcolor="white">

<head>
    <title>Portlet Widget Tag Library</title>
</head>

<body>

<gs:form action="boobie">

    <table cellspacing=2 cellpadding=2 border=0>
    <tr>
    <td align="right">Username: </td>
    <td align="left">
        <gs:textfield name="username" size="8" maxlength="20"></gs:textfield>
    </td>
    </tr>
    <tr>
    <td align="right">Password: </td>
    <td align="left">
        <gs:password name="password" size="8" maxlength="20"></gs:password>
    </td>
    </tr>
    <tr>
    <td colspan=5 align="center"><gs:input type="submit" name="option" value="Login"></gs:input></td>
    </tr>
    </table>

</gs:form>


</body>

</html>