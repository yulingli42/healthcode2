package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.ClazzDao;
import com.healthcode.domain.Clazz;
import com.healthcode.service.IClazzService;

import java.util.List;
import java.util.Objects;

public class ClazzServiceImpl implements IClazzService {
    private final ClazzDao clazzDao = new ClazzDao();

    @Override
    public List<Clazz> getAllClazzByMajor(Integer majorId) {
        //校验数据
        if (Objects.isNull(majorId)){
            throw new HealthCodeException("信息不可为空");
        }
        //根据专业获取所有班级信息
        return clazzDao.listAll(majorId);
    }

    @Override
    public void addClazz(Integer majorId, String name) {
        //校验数据
        if (Objects.isNull(majorId) || "".equals(name)){
            throw new HealthCodeException("信息不可为空");
        }
        //添加班级
        clazzDao.insert(majorId, name);
    }

    @Override
    public Clazz getClazzById(Integer id) {
        //校验数据
        if (Objects.isNull(id)){
            throw new HealthCodeException("信息不可为空");
        }
        return clazzDao.getById(id);
    }
}
