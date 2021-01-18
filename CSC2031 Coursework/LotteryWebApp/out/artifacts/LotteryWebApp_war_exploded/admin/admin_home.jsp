<%--
  Created by IntelliJ IDEA.
  User: zzilv
  Date: 2020-11-22
  Time: 18:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<%-- Prevents path traversal--%>
<%
    response.setHeader("Cache-Control", "no-cache,no_store, must-revalidate");

    if(session.getAttribute("admin_login") == null || session.getAttribute("admin_login") == "")
        response.sendRedirect("index.jsp");
%>






<h4>Welcome, <%=session.getAttribute("admin_login")%></h4>



<%if(request.getAttribute("data")!=null){%>
    <%= request.getAttribute("data") %>
<%}%>







</body>
</html>
