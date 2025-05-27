package controller;

import dao.CustomerDAO;
import dao.TrainerDAO;
import dao.UserDAO;
import model.Customer;
import model.Trainer;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Basic validation
        if(name == null || name.trim().isEmpty() || gender == null || gender.trim().isEmpty()) {
            request.setAttribute("error", "Name and Gender are required.");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }

        // Update user common fields
        user.setName(name);
        user.setGender(gender);
        user.setPhone(phone);
        user.setAddress(address);

        UserDAO userDAO = new UserDAO();
        boolean userUpdated = userDAO.updateUserProfile(user);

        if (!userUpdated) {
            request.setAttribute("error", "Failed to update user information.");
            request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
            return;
        }

        if ("Customer".equalsIgnoreCase(role)) {
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = (Customer) session.getAttribute("customer");
            if (customer == null) {
                customer = new Customer();
                customer.setId(user.getId());
            }

            String weightStr = request.getParameter("weight");
            String heightStr = request.getParameter("height");
            String goal = request.getParameter("goal");
            String medicalConditions = request.getParameter("medicalConditions");

            try {
                customer.setWeight(weightStr != null && !weightStr.isEmpty() ? Double.parseDouble(weightStr) : null);
                customer.setHeight(heightStr != null && !heightStr.isEmpty() ? Double.parseDouble(heightStr) : null);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Weight and Height must be valid numbers.");
                request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
                return;
            }

            customer.setGoal(goal);
            customer.setMedicalConditions(medicalConditions);

            boolean custUpdated = customerDAO.updateCustomerProfile(customer);
            if (!custUpdated) {
                request.setAttribute("error", "Failed to update customer information.");
                request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
                return;
            }
            session.setAttribute("customer", customer);

        } else if ("Trainer".equalsIgnoreCase(role)) {
            TrainerDAO trainerDAO = new TrainerDAO();
            Trainer trainer = (Trainer) session.getAttribute("trainer");
            if (trainer == null) {
                trainer = new Trainer();
                trainer.setId(user.getId());
            }

            String experienceYearsStr = request.getParameter("experienceYears");
            String specialization = request.getParameter("specialization");
            String description = request.getParameter("description");

            try {
                trainer.setExperienceYears(experienceYearsStr != null && !experienceYearsStr.isEmpty() ? Integer.parseInt(experienceYearsStr) : null);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Years of Experience must be a valid number.");
                request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
                return;
            }

            trainer.setSpecialization(specialization);
            trainer.setDescription(description);

            boolean trainerUpdated = trainerDAO.updateTrainerProfile(trainer);
            if (!trainerUpdated) {
                request.setAttribute("error", "Failed to update trainer information.");
                request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
                return;
            }
            session.setAttribute("trainer", trainer);
        }

        // Update user in session with latest info
        session.setAttribute("user", user);
        request.setAttribute("message", "Profile updated successfully.");
        request.getRequestDispatcher("edit_profile.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("edit_profile.jsp");
    }
}