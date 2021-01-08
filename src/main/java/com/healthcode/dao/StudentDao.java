package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
