<%@ page isErrorPage="true" %>

<br>
	<%= exception.getMessage() %>
<p></p>
	<b>Stack Trace:</b><br>
        <% exception.printStackTrace(new java.io.PrintWriter(out)); %>



