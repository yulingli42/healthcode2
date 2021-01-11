package com.healthcode.dao;

import com.google.common.collect.Lists;
import com.healthcode.common.HealthCodeException;
import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Clazz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhenghong
 */
public class ClazzDao {
    private final MajorDao majorDao = new MajorDao();

    public Clazz getById(Integer id) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("" +
                    "SELECT name, profession_id FROM class WHERE id = ?")) {
                statement.setInt(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Clazz clazz = new Clazz();
                        clazz.setId(id);
                        clazz.setName(resultSet.getString("name"));
                        clazz.setMajor(majorDao.getById(resultSet.getInt("profession_id")));
                        return clazz;
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取班级失败");
        }
    }

    public List<Clazz> listAll(Integer majorId) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM class WHERE profession_id = ?")) {
                statement.setInt(1, majorId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    ArrayList<Clazz> list = Lists.newArrayList();
                    while (resultSet.next()) {
                        Clazz clazz = new Clazz();
                        clazz.setId(resultSet.getInt("id"));
                        clazz.setName(resultSet.getString("name"));
                        clazz.setMajor(majorDao.getById(majorId));
                        list.add(clazz);
                    }
                    return list;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new HealthCodeException("获取全部班级失败");
        }
    }

    public Integer getClassIdByName(String className) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT id FROM class WHERE name = ?")) {
                statement.setString(1, className);
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
            throw new HealthCodeException("获取班级号失败");
        }
    }
}
