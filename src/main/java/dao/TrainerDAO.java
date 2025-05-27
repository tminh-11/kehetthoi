/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ACER
 */
import model.Trainer;
import connection.DbCon;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class TrainerDAO {
    public boolean updateTrainerProfile(Trainer trainer) {
        String sql = "UPDATE Trainer SET ExperienceYears=?, Description=?, Specialization=? WHERE Id=?";
        try (Connection conn = DbCon.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (trainer.getExperienceYears() != null) {
                stmt.setInt(1, trainer.getExperienceYears());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
            }
            stmt.setString(2, trainer.getDescription());
            stmt.setString(3, trainer.getSpecialization());
            stmt.setInt(4, trainer.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}