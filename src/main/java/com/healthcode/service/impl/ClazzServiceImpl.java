package com.healthcode.service.impl;

import com.healthcode.dao.ClazzDao;
import com.healthcode.domain.Clazz;
import com.healthcode.service.IClazzService;

import java.util.List;

public class ClazzServiceImpl implements IClazzService {
    private final ClazzDao clazzDao = new ClazzDao();

    @Override
    public List<Clazz> getAllClazzByMajor(Integer majorId) {
        //根据专业获取所有班级信息
        return clazzDao.listAll(majorId);
    }

    @Override
    public void addClazz(Integer majorId, String name) {
        //添加班级
        clazzDao.insert(majorId, name);
    }
}
