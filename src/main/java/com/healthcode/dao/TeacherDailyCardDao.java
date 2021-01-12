package com.healthcode.dao;

import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.TeacherDailyCard;
import com.healthcode.utils.JudgeHealthCodeTypeUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author zhenghong
 */
public class TeacherDailyCardDao {
    public TeacherDailyCard getToDayCardByTeacherID(String teacherId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id, result, create_time " +
                            "FROM teacher_daily_card WHERE teacher_id = ? AND" +
                            " create_time < CAST(CURDATE() + 1 AS DATE ) AND create_time > CAST(CURDATE() AS DATE)")) {
                statement.setString(1,teacherId);
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
            e.printStackTrace();
            throw new HealthCodeException("获取教师每日一报失败");
        }
    }

    public List<HealthCodeType> judgeHealthCodeTypeByPast(String teacherId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM (SELECT id, result, create_time FROM teacher_daily_card " +
                            "WHERE teacher_id = ? ORDER BY create_time DESC LIMIT 14) AS TEMP ORDER BY create_time")) {
                return JudgeHealthCodeTypeUtil.getHealthCodeTypes(teacherId, statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("检查教师过去打卡记录失败");
        }
    }
}
