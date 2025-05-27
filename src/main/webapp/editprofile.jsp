<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@page import="model.User"%>
<%@page import="model.Customer"%>
<%@page import="model.Trainer"%>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    String role = user.getRole();
    Customer customer = null;
    Trainer trainer = null;

    // Assuming you have methods to retrieve customer and trainer details
    if ("Customer".equalsIgnoreCase(role)) {
        customer = (Customer) session.getAttribute("customer");
    } else if ("Trainer".equalsIgnoreCase(role)) {
        trainer = (Trainer) session.getAttribute("trainer");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="style/editprofile.css" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    
</head>
<body>
<div class="container">
    <h2><i class="fa fa-user-edit"></i> Edit Profile</h2>
    <form action="edit-profile" method="post">
        <label for="name">Name: <i class="fa fa-user"></i></label>
        <input id="name" type="text" name="name" value="<%= user.getName() %>" required/>

        <label for="gender">Gender: <i class="fa fa-venus-mars"></i></label>
        <select id="gender" name="gender" required>
            <option value="Male" <%= "Male".equals(user.getGender()) ? "selected" : "" %>>Male</option>
            <option value="Female" <%= "Female".equals(user.getGender()) ? "selected" : "" %>>Female</option>
            <option value="Other" <%= "Other".equals(user.getGender()) ? "selected" : "" %>>Other</option>
        </select>

        <label for="phone">Phone: <i class="fa fa-phone"></i></label>
        <input id="phone" type="text" name="phone" value="<%= user.getPhone() != null ? user.getPhone() : "" %>" pattern="0[0-9]{9}" title="Phone number must be 10 digits starting with 0"/>



        <label for="address">Address: <i class="fa fa-map-marker-alt"></i></label>
        <input id="address" type="text" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>"/>

        <% if ("Customer".equalsIgnoreCase(role)) { %>
            <h3><i class="fa fa-user"></i> Customer Information</h3>
            <label for="weight">Weight (kg): <i class="fa fa-weight"></i></label>
            <input id="weight" type="number" step="0.1" name="weight" value="<%= customer != null ? customer.getWeight() : "" %>"/>

            <label for="height">Height (cm): <i class="fa fa-ruler-vertical"></i></label>
            <input id="height" type="number" step="0.1" name="height" value="<%= customer != null ? customer.getHeight() : "" %>"/>


            <label for="goal">Goals: <i class="fa fa-bullseye"></i></label>
            <textarea id="goal" name="goal" rows="3"><%= customer != null ? customer.getGoal() : "" %></textarea>

            <label for="medicalConditions">Medical Conditions: <i class="fa fa-notes-medical"></i></label>
            <textarea id="medicalConditions" name="medicalConditions" rows="3"><%= customer != null ? customer.getMedicalConditions() : "" %></textarea>
        <% } else if ("Trainer".equalsIgnoreCase(role)) { %>
            <h3><i class="fa fa-user-tie"></i> Trainer Information</h3>
            <label for="experienceYears">Years of Experience: <i class="fa fa-calendar-alt"></i></label>
            <input id="experienceYears" type="number" name="experienceYears" value="<%= trainer != null ? trainer.getExperienceYears() : "" %>"/>


            <label for="specialization">Specialization: <i class="fa fa-star"></i></label>
            <input id="specialization" type="text" name="specialization" value="<%= trainer != null ? trainer.getSpecialization() : "" %>"/>


            <label for="description">Description: <i class="fa fa-align-left"></i></label>
            <textarea id="description" name="description" rows="4"><%= trainer != null ? trainer.getDescription() : "" %></textarea>
        <% } %>


        <button type="submit"><i class="fa fa-save"></i> Save</button>
    </form>

    <c:if test="${not empty error}">
        <p class="error"><i class="fa fa-exclamation-triangle"></i> ${error}</p>
    </c:if>
    <c:if test="${not empty message}">
        <p class="message"><i class="fa fa-check-circle"></i> ${message}</p>
    </c:if>


    <a href="profile.jsp"><i class="fa fa-arrow-left"></i> Back to Profile</a>
</div>
</body>
</html>