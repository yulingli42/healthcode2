package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Teacher;

import java.sql.*;
import java.util.Date;

/**
 * @author zhenghong
 */
public class TeacherDao {
    private final CollegeDao collegeDao = new CollegeDao();

    public Teacher getByUsername(String teacherId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, password, college_id, id_card " +
                            "FROM teacher WHERE id = ?")) {
                statement.setString(1,teacherId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    int collegeId = resultSet.getInt("college_id");
                    String idCard = resultSet.getString("id_card");

                    Teacher teacher = new Teacher();
                    teacher.setName(name);
                    teacher.setPassword(password);
                    teacher.setCollege(collegeDao.getById(collegeId));
                    teacher.setIdCard(idCard);

                    return teacher;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitDailyCard(Teacher teacher, HealthCodeType healthCodeType){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO student_daily_card (student_id, result, create_time) VALUES (?, ?, ?)")) {
                statement.setInt(1, teacher.getId());
                statement.setString(2, healthCodeType.getType());
                statement.setTimestamp(3, new Timestamp(new Date().getTime()));
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
