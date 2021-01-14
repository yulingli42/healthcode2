package com.healthcode.service;

import com.healthcode.domain.College;

import java.util.List;

/**
 * @author qianlei
 */
public interface ICollegeService {
    /**
     * 获取所有的学院信息
     *
     * @return 所有的学院信息
     */
    List<College> getAllCollege();

    /**
     * 根据学院编号获取学院信息
     *
     * @param id 学院编号
     * @return 学院信息
     */
    College getCollegeById(Integer id);

    /**
     * 添加学院
     *
     * @param name 学院名
     */
    void addCollege(String name);

    /**
     * 根据id删除
     *
     * @param id 学院id
     */
    void deleteById(Integer id);
}
