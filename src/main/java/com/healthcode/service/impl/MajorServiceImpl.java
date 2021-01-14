package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.MajorDao;
import com.healthcode.domain.Major;
import com.healthcode.service.IMajorService;

import java.util.List;
import java.util.Objects;

/**
 * @author zhenghong
 */
public class MajorServiceImpl implements IMajorService {
    private final MajorDao majorDao = new MajorDao();

    @Override
    public List<Major> getAllMajorByCollegeId(Integer collegeId) {
        //校验数据
        if (Objects.isNull(collegeId)){
            throw new HealthCodeException("信息不可为空");
        }
        //根据学院号获取所有专业
        return majorDao.listAll(collegeId);
    }

    @Override
    public void addMajor(Integer collegeId, String name) {
        //校验数据
        if (Objects.isNull(collegeId) || "".equals(name)){
            throw new HealthCodeException("信息不可为空");
        }
        //添加专业
        majorDao.insert(collegeId, name);
    }

    @Override
    public Major getMajorById(Integer id) {
        //校验数据
        if (Objects.isNull(id)){
            throw new HealthCodeException("信息不可为空");
        }
        return majorDao.getById(id);
    }
}
