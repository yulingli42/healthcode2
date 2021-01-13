package com.healthcode.dao;

import com.google.common.collect.Lists;
import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenghong
 */
public class MajorDao {
    private final CollegeDao collegeDao = new CollegeDao();

    public Major getById(Integer id) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, college_id FROM profession WHERE id = ?")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Major major = new Major();
                        major.setId(id);
                        major.setName(resultSet.getString("name"));
                        major.setCollege(collegeDao.getById(resultSet.getInt("college_id")));
                        return major;
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取专业失败");
        }
    }

    public List<Major> listAll(Integer collegeId){
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM profession WHERE college_id = ?")) {
                statement.setInt(1, collegeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    ArrayList<Major> list = Lists.newArrayList();
                    while (resultSet.next()) {
                        Major major = new Major();
                        major.setId(resultSet.getInt("id"));
                        major.setName(resultSet.getString("name"));
                        major.setCollege(collegeDao.getById(collegeId));
                        list.add(major);
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取全部专业失败");
        }
    }

    public int insert(Integer collegeId, String name) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO profession(college_id, name) VALUES (?, ?)")) {
                statement.setInt(1, collegeId);
                statement.setString(2, name);

                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("添加专业失败");
        }
    }

}
