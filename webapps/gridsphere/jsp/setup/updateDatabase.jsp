

<div style="padding-top: 3px; margin: 0px 0px 0px 10px; padding-left: 8px; padding-right:
 5px; padding-bottom: 1px;">

    <h1>GridSphere Update</h1>

    <% String errMsg = (String)request.getAttribute("error");
       if (errMsg != null) { %>

    <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>
           
    <%   } %>

    GridSphere has detected that a new version of the portal has been installed! To continue, the existing database schemas
    must be updated. You should first make a back-up of your existing database before completing this step.
    <p/>
    Do you wish to proceed?
    <p/>

        <form method="POST" action="<%= request.getContextPath() %>/setup?install=update">


            <input type="submit" value="Update Database >>"/>

        </form>

</div>
