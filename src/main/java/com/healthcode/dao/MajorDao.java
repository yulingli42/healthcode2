package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Major;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MajorDao {
    private final CollegeDao collegeDao = new CollegeDao();

    public Major getById(Integer id) {
        try (Connection connection = DatasourceConfig.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("" +
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
            throw new RuntimeException(e);
        }
    }
}
