

<div style="padding-top: 3px; padding-left: 8px; padding-right: 5px; margin-left: 10px;">

    <h1>GridSphere Setup</h1>


    <h2>Create a Portal Administrator</h2>

The portal administrator has access to all administrative capabilities of the portal including adding/editing users, roles
and layouts. After creating an account, you will be able to login using the provided user name and password.

    <p/>

    <% String errMsg = (String)request.getAttribute("error");
           if (errMsg != null) { %>

        <fieldset><span style="color: red; "><%= errMsg %></span></fieldset>

        <%   } %>

    <p/>

<form action="<%= request.getContextPath() %>/setup?install=admin" method="POST">

   <table>
       <tr>
           <td align="right">
               <label for="username">User Name:</label>
           </td>
           <td align="left">
                <input type="text" name="username" id="username">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="firstname">First Name:</label>
           </td>
           <td align="left">
                <input type="text" name="firstname" id="firstname">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="lastname">Last Name:</label>
           </td>
           <td align="left">
                <input type="text" name="lastname" id="lastname">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="email">Email Address:</label>
           </td>
           <td align="left">
                <input type="text" name="email" id="email">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="organization">Organization:</label>
           </td>
           <td align="left">
                <input type="text" name="organization" id="organization">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="password">Password:</label>
           </td>
           <td align="left">
                <input type="password" name="password" id="password">
           </td>
       </tr>
       <tr>
           <td align="right">
               <label for="password2">Confirm Password:</label>
           </td>
           <td align="left">
                <input type="password" name="password2" id="password2">
           </td>
       </tr>

   </table>

  <p/>

    <input type="submit" value="Create Account"/>

</form>
</div>