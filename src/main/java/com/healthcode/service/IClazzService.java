package com.healthcode.service;

import com.healthcode.domain.Clazz;

import java.util.List;

/**
 * @author qianlei
 */
public interface IClazzService {
    /**
     * 根据专业编号获取该专业所有的班级信息
     * @param majorId 班级信息
     * @return 该专业所有的班级信息
     */
    List<Clazz> getAllClazzByMajor(Integer majorId);
}
