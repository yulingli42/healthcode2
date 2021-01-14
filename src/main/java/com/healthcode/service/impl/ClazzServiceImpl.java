package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.ClazzDao;
import com.healthcode.dao.StudentDao;
import com.healthcode.domain.Clazz;
import com.healthcode.service.IClazzService;

import java.util.List;
import java.util.Objects;

public class ClazzServiceImpl implements IClazzService {
    private final ClazzDao clazzDao = new ClazzDao();
    private final StudentDao studentDao = new StudentDao();

    @Override
    public List<Clazz> getAllClazzByMajor(Integer majorId) {
        //校验数据
        if (Objects.isNull(majorId)) {
            throw new HealthCodeException("信息不可为空");
        }
        //根据专业获取所有班级信息
        return clazzDao.listAllByMajorId(majorId);
    }

    @Override
    public void addClazz(Integer majorId, String name) {
        //校验数据
        if (Objects.isNull(majorId) || "".equals(name)) {
            throw new HealthCodeException("信息不可为空");
        }
        //添加班级
        clazzDao.insert(majorId, name);
    }

    @Override
    public Clazz getClazzById(Integer id) {
        //校验数据
        if (Objects.isNull(id)) {
            throw new HealthCodeException("信息不可为空");
        }
        return clazzDao.getById(id);
    }

    @Override
    public void deleteById(Integer id) {
        if (!studentDao.listAllByClazzId(id).isEmpty()) {
            throw new HealthCodeException("请先删除该班级所有学生");
        }
        clazzDao.deleteById(id);
    }
}
