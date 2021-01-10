package com.healthcode.dao;

import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.StudentDailyCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            e.printStackTrace();
            throw new HealthCodeException("获取学生每日一报失败");
        }
    }

    public List<HealthCodeType> judgeHealthCodeTypeByPast(String studentId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM (SELECT id, result, create_time FROM student_daily_card " +
                            "WHERE student_id = ? ORDER BY create_time DESC LIMIT 14) AS TEMP ORDER BY create_time")) {
                statement.setString(1,studentId);
                List<HealthCodeType> healthCodeTypeList = new ArrayList<>();
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()){
                        healthCodeTypeList.add(HealthCodeType.of(resultSet.getString("result")));
                    }
                }
                return healthCodeTypeList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("检查学生过去打卡记录失败");
        }
    }
}
