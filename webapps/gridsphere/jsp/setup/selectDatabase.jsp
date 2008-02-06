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

<script type="text/javascript">
<!--
function SelectDriver() {
    var dbtype = document.dbform.dbtype.value;
    var connURL ="";
    var driver = "";
    var dialect = "";
    if (dbtype == "postgresql") {
        connURL = "jdbc:postgresql://localhost:5432/gridsphere";
        dialect = "org.hibernate.dialect.PostgreSQLDialect";
        driver = "org.postgresql.Driver";
    } else if (dbtype == "mysql") {
        connURL = "jdbc:mysql://localhost:3306/gridsphere";
        dialect = "org.hibernate.dialect.MySQLDialect";
        driver = "com.mysql.jdbc.Driver";
    } else if (dbtype == "oracle") {
        connURL = "jdbc:oracle:thin:@localhost:1521:gridsphere";
        dialect = "org.hibernate.dialect.Oracle9Dialect";
        driver = "oracle.jdbc.driver.OracleDriver";
    } else if (dbtype == "ms") {
        connURL = "jdbc:sqlserver://localhost:1681/gridsphere";
        dialect = "org.hibernate.dialect.SQLServerDialect";
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }
    document.dbform.databaseURL.value = connURL;
    document.dbform.driverClass.value = driver;
    document.dbform.dialect.value = dialect;

    //alert(dbtype);
}

function DisplayWait( formName ) {

    var waitDiv = document.getElementById("content");

    waitDiv.innerHTML = '<div style="position: absolute; padding: 20px; border: solid 5px; background-color: white; left: 300px; width: 300px; z-index: 5;">  <%= getLocalizedText("SETUP_DB_CREATE_PLEASE_WAIT",request) %></div>';

    document.dbform.custom.disabled = true;
    document.simple.standard.disabled = true;
    document[formName].submit();
}

// -->
</script>



<div style="padding-top: 3px; padding-left: 8px; padding-right: 5px; margin-left: 10px;">

    <h1><%= getLocalizedText("SETUP_DB_CREATE_SETUP_GRIDSPHERE",request) %></h1>



    <% String errMsg = (String)request.getAttribute("error");
       if (errMsg != null) { %>

    <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>
           
    <%   } %>




    <h2><%= getLocalizedText("SETUP_DB_CREATE_CHOOSE_DB_CONFIG",request) %></h2>

    <%= getLocalizedText("SETUP_DB_CREATE_WHERE_TO_STORE",request) %>

    <p/>


    <fieldset>
        <legend><%= getLocalizedText("SETUP_DB_CREATE_EMBEDDED_TITLE",request) %></legend>

        <%= getLocalizedText("SETUP_DB_CREATE_EMBEDDED_DESC",request) %>
        <p/>
        <form method="POST" name="simple" action="<%= request.getContextPath() %>/setup?install=default">


            <input type="submit" name="standard" value="<%= getLocalizedText("SETUP_DB_CREATE_EMBEDDED_BUTTON",request) %>" onclick="DisplayWait( this.form.name )"/>

        </form>

    </fieldset>

    <p/>


    <div id="content"></div>

    <fieldset>
        <legend><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_TITLE",request) %></legend>

        <%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_DESC",request) %>
        <p/>
        <form name="dbform" method="POST" action="<%= request.getContextPath() %>/setup?install=custom">

            <table>
                <tr>
                    <td align="right">
                        <label for="dbtype"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_TYPE",request) %>:</label>
                    </td>
                    <td align="left">
                        <select name="dbtype" id="dbtype" onchange="SelectDriver()">
                            <option value="postgresql">PostgreSQL</option>
                            <option value="mysql">MySQL</option>
                            <option value="oracle">Oracle 9/10g</option>
                            <option value="ms">MS SQL Server</option>
                            <option value="other"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_TYPE_OTHER",request) %></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="connection"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_URL",request) %>:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="databaseURL" id="connection" size="40"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="connection"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_DRIVER_CLASS",request) %>:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="driverClass" id="driverclass" size="40"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="connection"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_DIALECT",request) %>:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="dialect" id="dialect" size="40"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="username"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_USERNAME",request) %>:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="username" id="username"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="password"><%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_FIELD_PASSWORD",request) %>:</label>
                    </td>
                    <td align="left">
                        <input type="password" name="password" id="password"/>
                    </td>
                </tr>
            </table>
            <p/>
            <input type="submit" name="custom" value="<%= getLocalizedText("SETUP_DB_CREATE_EXTERNAL_BUTTON",request) %>" onsubmit="DisplayWait( this.form.name )"/>
        </form>

    </fieldset>

</div>

<script type="text/javascript">
<!--
    SelectDriver();
// -->
</script>