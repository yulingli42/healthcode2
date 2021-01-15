package com.healthcode.service;

import com.healthcode.domain.Clazz;

import java.util.List;

/**
 * @author qianlei
 */
public interface IClazzService {
    /**
     * 根据专业编号获取该专业所有的班级信息
     *
     * @param majorId 班级信息
     * @return 该专业所有的班级信息
     */
    List<Clazz> getAllClazzByMajor(Integer majorId);

    /**
     * 添加班级
     *
     * @param majorId 专业id
     * @param name    班级
     */
    void addClazz(Integer majorId, String name);

    /**
     * 根据班级id获取班级信息
     *
     * @param id 班级id
     * @return 班级信息
     */
    Clazz getClazzById(Integer id);

    /**
     * 删除根据班级号
     * @param id 班级号
     */
    void deleteById(Integer id);
}
