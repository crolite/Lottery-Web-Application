<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Home</title>
  </head>
  <body>
  <%-- Tries to login--%>
  <% if (session.getAttribute("tries")==null){%>
    <%  session.setAttribute("tries", 3); %>
  <%}%>




  <h1> Home Page</h1>
  <% if (request.getAttribute("message")!=null) { %>
    <p><%= request.getAttribute("message") %></p>
  <%}%>

<h3> New users please register</h3>
  <form action="CreateAccount" method="post"><%-- User Registration form--%>
      <label for="firstname">First name:</label><br>
      <input type="text" id="firstname" name="firstname" required><br>
      <label for="lastname">Last name:</label><br>
      <input type="text" id="lastname" name="lastname" required><br>
      <label for="username">Username:</label><br>
      <input type="text" id="username" name="username" required><br>
      <label for="phone ">Phone number:</label><br>
      <input type="tel"  id="phone " name="phone" pattern="[0-9]{2}-[0-9]{4}-[0-9]{7}" required title="Phone number must be of the form xx-xxxx-xxxxxxx (e.g. 44-0191-1234567)" placeholder="44-0191-1234567"><br>
      <label for="email">Email:</label><br>
      <input type="email" id="email" name="email" required  placeholder="joebloggs@email.com"><br>
      <label for="password">Password:</label><br>
      <input type="password" id="password" name="password"  minlength="8" maxlength="15" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8, but less than 15" required placeholder="Password123"><br>
      <label>Select Type</label>
      <select name="txt_role" id="txt_role" required>
          <option value="" selected="selected"> - select role - </option>
          <option value="public">Public</option>
          <option value="admin">Admin</option>
      </select><br>
      <input type="submit" value="Submit">
  </form>

<h3>Existing users please login</h3>



  <form action="UserLogin" method="post"><%-- User Login form --%>
      <% if (session.getAttribute("tries")!=null){%> <%-- If login attemps reached form is disabled--%>
         <% int j=(int)session.getAttribute("tries");%>
        <%if (j<=0){%>
            <fieldset disabled="disabled">
         <%}%>
      <%}%>
        <label for="username">Username:</label><br>
         <input type="text" id="username" name="username" required><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password"  minlength="8" maxlength="15" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8, but less than 15" required placeholder="Password123"><br><br>
                <label>Select Type</label>
                <select name="txt_role" id="txt_role" required>
                    <option value="" selected="selected"> - select role - </option>
                    <option value="admin">Admin</option>
                    <option value="public">Public</option>
                </select><br>
        <input type="submit" value="Submit">
      </fieldset>
  </form>

  </body>
</html>
