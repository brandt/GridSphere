<jsp:useBean id="error" class="java.lang.Throwable" scope="request"/>

<p><b>An error occured!</b><p>
<br>
	<%= error.getMessage() %>
<p></p>
	<b>Stack Trace:</b><br>
        <% error.printStackTrace(new java.io.PrintWriter(out)); %>

