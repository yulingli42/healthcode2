package com.healthcode.dao;

import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhenghong
 */
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
            e.printStackTrace();
            throw new HealthCodeException("获取管理员失败");
        }
    }

    public void insert(String username, String password, Admin.AdminRole adminRole, int collegeId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO admin(username, password, role, college_id) VALUES (?, ?, ?, ?)")) {
                statement.setString(1,username);
                statement.setString(2,password);
                statement.setString(3,adminRole.getDesc());
                statement.setInt(4,collegeId);

                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("添加管理员失败");
        }
    }

    public void alter(String username, String newUsername, String password, Admin.AdminRole adminRole, int collegeId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE admin SET username = ?, password = ?, role = ?, college_id = ? WHERE (username = ?)")) {
                statement.setString(1,newUsername);
                statement.setString(2,password);
                statement.setString(3,adminRole.getDesc());
                statement.setInt(4,collegeId);
                statement.setString(5, username);

                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("更新管理员失败");
        }
    }
}
