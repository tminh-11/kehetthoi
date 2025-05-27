/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ACER
 */
import model.Customer;
import connection.DbCon;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class CustomerDAO {
    public boolean updateCustomerProfile(Customer customer) {
        String sql = "UPDATE Customer SET Weight=?, Height=?, Goal=?, Medical_conditions=? WHERE Id=?";
        try (Connection conn = DbCon.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (customer.getWeight() != null) {
                stmt.setDouble(1, customer.getWeight());
            } else {
                stmt.setNull(1, java.sql.Types.FLOAT);
            }
            if (customer.getHeight() != null) {
                stmt.setDouble(2, customer.getHeight());
            } else {
                stmt.setNull(2, java.sql.Types.FLOAT);
            }
            stmt.setString(3, customer.getGoal());
            stmt.setString(4, customer.getMedicalConditions());
            stmt.setInt(5, customer.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}