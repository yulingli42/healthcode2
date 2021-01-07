package com.healthcode.dao;

import com.healthcode.config.DatasourceConfig;
import com.healthcode.domain.Clazz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            throw new RuntimeException(e);
        }
    }
}
