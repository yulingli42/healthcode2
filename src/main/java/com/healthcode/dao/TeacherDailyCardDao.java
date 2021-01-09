package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.TeacherDailyCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author zhenghong
 */
public class TeacherDailyCardDao {
    public TeacherDailyCard getToDayCardByTeacherID(Integer teacherId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, result, create_time " +
                            "FROM teacher_daily_card WHERE teacher_id = ? AND" +
                            " create_time < CAST(CURDATE() + 1 AS DATE ) AND create_time > CAST(CURDATE() AS DATE)")) {
                statement.setInt(1,teacherId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) {
                        return null;
                    }

                    TeacherDailyCard teacherDailyCard = new TeacherDailyCard();
                    teacherDailyCard.setId(resultSet.getInt("id"));
                    teacherDailyCard.setCreateTime(resultSet.getTimestamp("create_time").toLocalDateTime());

                    teacherDailyCard.setResult(HealthCodeType.of(resultSet.getString("result")));

                    return teacherDailyCard;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
