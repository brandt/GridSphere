
<%@ taglib uri="/portletWidgets" prefix="gs" %>

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
        <input type="text"
            name="username"
            size="8"
            maxlength="20"
            value="">
        </input>
    </td>
    </tr>
    <tr>
    <td align="right">Password: </td>
    <td align="left">
        <input type="password"
            name="password"
            size="8"
            maxlength="20">
        </input>
    </td>
    </tr>
    <tr>
    <td colspan=5 align="center"><input type="submit" name="option" value="Login" ></input></td>
    </tr>
    </table>

</gs:form>


</body>

</html>