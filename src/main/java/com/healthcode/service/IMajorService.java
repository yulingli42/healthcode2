package com.healthcode.service;

import com.healthcode.domain.Major;

import java.util.List;

/**
 * @author qianlei
 */
public interface IMajorService {
    /**
     * 根据学院编号获取该学院所有专业
     *
     * @param collegeId 学院编号
     * @return 该学院所有专业
     */
    List<Major> getAllMajorByCollegeId(Integer collegeId);

    /**
     * 添加专业
     *
     * @param collegeId 学院id
     * @param name      名称
     */
    void addMajor(Integer collegeId, String name);

    /**
     * 根据专业 id 获取专业信息
     *
     * @param id 专业 id
     * @return 专业信息
     */
    Major getMajorById(Integer id);
}
