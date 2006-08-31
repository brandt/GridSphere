
<%@ page isErrorPage="true" %>

<div style="padding: 0px 0px 5px 0px; margin: 10px">
<h2>Unexpected server error!</h2>

<b>HTTP Status Code:</b>  <b style="color: red;"><%= request.getAttribute("javax.servlet.error.status_code") %></b>
<p/>
<b>Originating URI:</b> <b style="color: red;"><%= request.getAttribute("javax.servlet.error.request_uri") %></b>


<p/>


    <table style="border-collapse: collapse; width: 50em; border: 1px solid black;"><caption>Stack Trace</caption>
        <thead><tr><th scope="col">Class</th><th scope="col">Method</th><th scope="col">Line #</th></tr></thead>
  <% Throwable t = (Throwable)request.getAttribute("javax.servlet.error.exception");
     StackTraceElement[] elem = t.getStackTrace();
     for (int i = 0; i < 10; i++) {
      %>
<tr>
  <td><%= elem[i].getClassName() %> </td> <td> <%= elem[i].getMethodName() %> </td> <td> <%= elem[i].getLineNumber() %>  </td>
 </tr>
    <%
     }
     %>
   </table>

</div>