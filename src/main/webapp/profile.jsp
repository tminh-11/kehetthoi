<%-- 
    Document   : profile
    Created on : May 26, 2025, 8:11:13 PM
    Author     : ACER
--%>

<%@page import="model.User"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html>
<head>
    <title>Gympro Profile</title>
</head>
<body>
<div class="container">
    <h2>Your Profile</h2>
    <img src="<%= user.getAvatarUrl() != null ? user.getAvatarUrl() : "default_avatar.png" %>" alt="Avatar" class="avatar" />
    <p><strong>Name:</strong> <%= user.getName() %></p>
    <p><strong>Email:</strong> <%= user.getEmail() %></p>
    <p><strong>Gender:</strong> <%= user.getGender() %></p>
    <p><strong>Phone:</strong> <%= user.getPhone() != null ? user.getPhone() : "N/A" %></p>
    <p><strong>Address:</strong> <%= user.getAddress() != null ? user.getAddress() : "N/A" %></p>

    <form action="upload-avatar" method="post" enctype="multipart/form-data">
        <label>Change Avatar:</label>
        <input type="file" name="avatar" accept="image/*" required/>
        <button type="submit">Upload</button>
    </form>

    <a href="edit_profile.jsp">Edit Profile</a>
    <a href="change_password.jsp">Change Password</a>
    <a href="logout">Logout</a>
</div>
</body>
</html>