package com.healthcode.dao;

import com.google.common.collect.Lists;
import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.College;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qianlei
 */
public class CollegeDao {
    public List<College> listAll() {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT * FROM college ")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    ArrayList<College> list = Lists.newArrayList();
                    while (resultSet.next()) {
                        College college = new College();
                        college.setId(resultSet.getInt("id"));
                        college.setName(resultSet.getString("name"));
                        list.add(college);
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取全部学院失败");
        }
    }

    public int insert(College college) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO college(id, name) VALUES (?,?)")) {
                statement.setInt(1, college.getId());
                statement.setString(2, college.getName());
                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("添加学院失败");
        }
    }

    public int insert(String name) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO college(name) VALUES (?)")) {
                statement.setString(1, name);

                return statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("添加学院失败");
        }
    }

    public College getById(Integer id) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT name FROM college WHERE id = ?")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        College college = new College();
                        college.setId(id);
                        college.setName(resultSet.getString("name"));
                        return college;
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取学院失败");
        }
    }

    public Integer getCollegeIdByName(String collegeName) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id FROM college WHERE name = ?")) {
                statement.setString(1, collegeName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取学院号失败");
        }
    }
}
