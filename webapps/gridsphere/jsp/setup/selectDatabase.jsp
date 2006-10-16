

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

function DisplayWait() {

    var waitDiv = document.getElementById("content");

    waitDiv.innerHTML = '<div style="position: absolute; padding: 20px; border: solid 5px; background-color: white; left: 300px; width: 300px; z-index: 5;">  Please wait... database is being created!</div>';

    document.dbform.custom.disabled = true;
    document.simple.standard.disabled = true;
}

// -->
</script>



<div style="padding-top: 3px; padding-left: 8px; padding-right: 5px; margin-left: 10px;">

    <h1>GridSphere Setup</h1>



    <% String errMsg = (String)request.getAttribute("error");
       if (errMsg != null) { %>

    <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>
           
    <%   } %>




    <h2>Choose a Database Configuration</h2>

    Select where GridSphere should store its data

    <p/>


    <fieldset>
        <legend>Embedded Database</legend>

        The embedded database is provided by GridSphere and is <b>recommended for evaluation and
        demonstration purposes</b>. Production systems should consider using an external database for improved scalability and reliability.
        (This option will create a HSQL database in the gridsphere web application).
        <p/>
        <form method="POST" name="simple" action="<%= request.getContextPath() %>/setup?install=default">


            <input type="submit" name="standard" value="Embedded Database >>" onclick="DisplayWait()"/>

        </form>

    </fieldset>

    <p/>


    <div id="content"></div>

    <fieldset>
        <legend>External Database</legend>

        If you wish the portal to store its data in an external database, please provide the necessary connection values.
        This is <b>recommended for production systems</b>. You must also make sure the JDBC driver (JAR) is placed in the
        application classloader of the servlet container. (In the case of Tomcat, place JAR file in <b>$TOMCAT/common/lib</b>
        directory.
        <p/>
        <form name="dbform" method="POST" action="<%= request.getContextPath() %>/setup?install=custom">

            <table>
                <tr>
                    <td align="right">
                        <label for="dbtype">Choose your database:</label>
                    </td>
                    <td align="left">
                        <select name="dbtype" id="dbtype" onchange="SelectDriver()">
                            <option value="postgresql">PostgreSQL</option>
                            <option value="mysql">MySQL</option>
                            <option value="oracle">Oracle 9/10g</option>
                            <option value="ms">MS SQL Server</option>
                            <option value="other">Other</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="connection">Enter a Database URL:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="databaseURL" id="connection" size="40"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="connection">Enter the driver class name:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="driverClass" id="driverclass" size="40"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="connection">Enter the Hibernate dialect:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="dialect" id="dialect" size="40"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="username">Enter the User Name:</label>
                    </td>
                    <td align="left">
                        <input type="text" name="username" id="username"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label for="password">Enter the Password:</label>
                    </td>
                    <td align="left">
                        <input type="password" name="password" id="password"/>
                    </td>
                </tr>
            </table>
            <p/>
            <input type="submit" name="custom" value="External Database >>" onclick="DisplayWait()"/>
        </form>

    </fieldset>

</div>

<script type="text/javascript">
<!--
    SelectDriver();
// -->
</script>