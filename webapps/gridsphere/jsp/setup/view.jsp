

<h1>GridSphere Setup</h1>


<form method="POST" action="<%= request.getContextPath() %>/setup">


Select a database type:

<select name="dbtype" id="dbtype">
    <option selected="selected" value="org.hibernate.dialect.HSQLDialect">HsqlDB (default)</option>
    <option value="org.hibernate.dialect.PostgreSQLDialect">Postgres</option>
    <option value="org.hibernate.dialect.MySQLDialect">MySQL</option>
    <option value="org.hibernate.dialect.Oracle9Dialect">Oracle 9/10g</option>
</select>

Please provide the connection URL:

<input type="text" name="connectionURL" id="connection"/>

Please provide the user name

<input type="text" name="username" id="username"/>

Please provide the password

<input type="password" name="password" id="password"/>


    <input type="submit" value="Enter"/>

</form>