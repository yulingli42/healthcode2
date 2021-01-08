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
}
