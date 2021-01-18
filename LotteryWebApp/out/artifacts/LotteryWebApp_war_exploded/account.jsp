<%--
  Created by IntelliJ IDEA.
  User: johnmace
  Date: 21/10/2020
  Time: 16:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account</title>
</head>
<body>
<h1>User Account</h1>
<%-- Prevents path traversal--%>
<%
    response.setHeader("Cache-Control", "no-cache,no_store, must-revalidate");

    if(session.getAttribute("public_login") == null || session.getAttribute("public_login") == "")
        response.sendRedirect("index.jsp");
%>


<% if(request.getAttribute("message") != null){ %>
<p><%=request.getAttribute("message")%></p>
<% }else{ %>
<p></p>
<% }%>

<h3> User Details</h3>
<%-- Retrieves user's info--%>
<p><%= "Username: " + session.getAttribute("username") %></p>
<p><%= "First Name: " +session.getAttribute("firstname") %></p>
<p><%= "Last Name: " +session.getAttribute("lastname") %></p>
<p><%= "Email Name: " +session.getAttribute("email") %></p>


<h2>Select New Lottery Draw</h2><%-- Customization for button--%>
<style>
    .button {
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        margin: 4px 2px;
        cursor: pointer;
    }
    .button1 {background-color: #4CAF50;} /* Green */
</style>


<form action="AddUserNumbers" method="post"><%-- Form for filling 6 numbers between 0 and 60 --%>
    <label for="number1">Number 1:</label>
    <input type="number" id="number1" name="number1" min="0" max="60"  required ><br>
    <label for="number2">Number 2:</label>
    <input type="number" id="number2" name="number2" min="0" max="60"  required ><br>
    <label for="number3">Number 3:</label>
    <input type="number" id="number3" name="number3" min="0" max="60"  required><br>
    <label for="number4">Number 4:</label>
    <input type="number" id="number4" name="number4" min="0" max="60"  required><br>
    <label for="number5">Number 5:</label>
    <input type="number" id="number5" name="number5" min="0" max="60"  required><br>
    <label for="number6">Number 6:</label>
    <input type="number" id="number6" name="number6" min="0" max="60"  required><br><br>
    <input type="submit"  value="Submit"><br><br>
</form>



<script>
    function myFunction() { <%-- RNG function for filling in the 6 fields--%>
        var x = Math.floor((Math.random() * 60) + 0);
        document.getElementById("number1").value = x;
        var x = Math.floor((Math.random() * 60) + 0);
        document.getElementById("number2").value = x;
        var x = Math.floor((Math.random() * 60) + 0);
        document.getElementById("number3").value = x;
        var x = Math.floor((Math.random() * 60) + 0);
        document.getElementById("number4").value = x;
        var x = Math.floor((Math.random() * 60) + 0);
        document.getElementById("number5").value = x;
        var x = Math.floor((Math.random() * 60) + 0);
        document.getElementById("number6").value = x;

    }
</script>

<button class="button button1" onclick=myFunction()>SPIN TO WIN</button><br><br><%-- Button that activates the RNG function for the fields--%>

 <form action="GetUserNumbers" method="post"> <%-- Button that returns previous user draws--%>
    <input type="submit"  value="Get Draws"><br>
</form>

<% if( request.getAttribute("draws") != null){%>
    <% String[] G= (String[]) request.getAttribute("draws"); %>
    <%for( int i=0;i<G.length;i++ ){%>
        <% if(G[i]!=null){%>
            <p><%=  G[i]+"\n" %></p>
        <%}%>
    <%}%>
    <form action="CheckNumbers" method="post"> <%-- Button that checks if user has lucky numbers--%>
    <input type="submit"  value="Check numbers"><br>
</form>
<% }else{ %>
         <p></p>
    <% }%>



<a href="index.jsp">Home Page</a>

</body>
</html>

