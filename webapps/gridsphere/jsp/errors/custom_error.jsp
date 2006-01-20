<jsp:useBean id="error" class="java.lang.Throwable" scope="request"/>

<h3>An error occured!</h3>
Error message:    <%= error.getMessage() %>
<p>
    <b>Stack Trace:</b><br/>
    <% error.printStackTrace(new java.io.PrintWriter(out)); %>
</p>