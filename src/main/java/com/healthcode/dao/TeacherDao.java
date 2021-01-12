package com.healthcode.dao;

import com.google.common.collect.Lists;
import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.HealthCodeType;
import com.healthcode.domain.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                    teacher.setId(teacherId);
                    teacher.setName(name);
                    teacher.setPassword(password);
                    teacher.setCollege(collegeDao.getById(collegeId));
                    teacher.setIdCard(idCard);

                    return teacher;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取教师失败");
        }
    }

    public void submitDailyCard(Teacher teacher, HealthCodeType healthCodeType){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO teacher_daily_card (teacher_id, result, create_time) VALUES (?, ?, ?)")) {
                statement.setString(1, teacher.getId());
                statement.setString(2, healthCodeType.getType());
                statement.setTimestamp(3, new Timestamp(new Date().getTime()));
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("提交教师打卡失败");
        }
    }

    public List<Teacher> listAll() {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM teacher ")) {
                return listAllHelper(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取教师列表失败");
        }
    }

    public List<Teacher> listAllByCollegeId(Integer collegeId) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT teacher.* FROM college, profession, teacher " +
                            "WHERE college.id=profession.college_id " +
                            "AND teacher.college_id=college.id " +
                            "AND college.id= ? ")) {
                statement.setInt(1,collegeId);
                return listAllHelper(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取教师列表失败");
        }
    }

    private List<Teacher> listAllHelper(PreparedStatement statement) throws SQLException {
        ArrayList<Teacher> list = Lists.newArrayList();
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getString("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setPassword(resultSet.getString("password"));
                teacher.setCollege(collegeDao.getById(resultSet.getInt("college_id")));
                teacher.setIdCard(resultSet.getString("id_card"));
                list.add(teacher);
            }
            return list;
        }
    }

    public void insert(String id, String name, String password, int collegeId, String idCard){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO teacher(id, name, password, college_id, id_card) VALUES (?, ?, ?, ?, ?)")) {
                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, password);
                statement.setInt(4, collegeId);
                statement.setString(5, idCard);

                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("添加教师失败");
        }
    }
}
