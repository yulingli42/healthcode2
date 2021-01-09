package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.StudentDailyCard;

import java.sql.*;

/**
 * @author zhenghong
 */
public class StudentDailyCardDao {
    public StudentDailyCard getToDayCardByStudentID(String studentId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, result, create_time " +
                            "FROM student_daily_card WHERE student_id = ? AND" +
                            " create_time < CAST(CURDATE() + 1 AS DATE ) AND create_time > CAST(CURDATE() AS DATE)")) {
                statement.setString(1,studentId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }

                    StudentDailyCard studentDailyCard = new StudentDailyCard();
                    studentDailyCard.setId(resultSet.getInt("id"));
                    studentDailyCard.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());

                    studentDailyCard.setResult(HealthCodeType.of(resultSet.getString("result")));

                    return studentDailyCard;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
