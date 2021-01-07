package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {
    private final CollegeDao collegeDao = new CollegeDao();

    public Admin getByUsername(String username) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, password, role, college_id " +
                            "FROM admin WHERE username = ?")) {
                statement.setString(1,username);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    int id = resultSet.getInt("id");
                    String password = resultSet.getString("password");
                    String role = resultSet.getString("role");
                    int collegeId = resultSet.getInt("college_id");

                    Admin admin = new Admin();
                    admin.setId(id);
                    admin.setUsername(username);
                    admin.setPassword(password);
                    admin.setRole(Admin.AdminRole.of(role));
                    admin.setCollege(collegeDao.getById(collegeId));
                    return admin;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
