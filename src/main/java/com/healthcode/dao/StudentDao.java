package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Student;
import com.healthcode.domain.StudentDailyCard;

import java.sql.*;
import java.util.Date;

/**
 * @author zhenghong
 */
public class StudentDao {
    private final ClazzDao clazzDao = new ClazzDao();

    public Student getByUsername(String studentId) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, password, class_id, id_card " +
                            "FROM student WHERE id = ?")) {
                statement.setString(1,studentId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    int classId = resultSet.getInt("class_id");
                    String idCard = resultSet.getString("id_card");

                    Student student = new Student();
                    student.setId(studentId);
                    student.setName(name);
                    student.setPassword(password);
                    student.setClazz(clazzDao.getById(classId));
                    student.setIdCard(idCard);
                    return student;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void submitDailyCard(Student student, HealthCodeType healthCodeType){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO student_daily_card (student_id, result, create_time) VALUES (?, ?, ?)")) {
                statement.setString(1, student.getId());
                statement.setString(2, healthCodeType.getType());
                statement.setTimestamp(3, new Timestamp(new Date().getTime()));
                statement.execute();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
