package com.healthcode.service.impl;

import com.healthcode.common.HealthCodeException;
import com.healthcode.dao.CollegeDao;
import com.healthcode.dao.MajorDao;
import com.healthcode.dao.TeacherDao;
import com.healthcode.domain.College;
import com.healthcode.service.ICollegeService;

import java.util.List;
import java.util.Objects;

/**
 * @author zhenghong
 */
public class CollegeServiceImpl implements ICollegeService {
    private final CollegeDao collegeDao = new CollegeDao();
    private final MajorDao majorDao = new MajorDao();
    private final TeacherDao teacherDao = new TeacherDao();

    @Override
    public List<College> getAllCollege() {
        //获取全部学院信息
        return collegeDao.listAll();
    }

    @Override
    public College getCollegeById(Integer id) {
        //校验数据
        if (Objects.isNull(id)) {
            throw new HealthCodeException("信息不可为空");
        }
        //获取单个学院信息
        return collegeDao.getById(id);
    }

    @Override
    public void addCollege(String name) {
        //校验数据
        if ("".equals(name)) {
            throw new HealthCodeException("信息不可为空");
        }
        //添加学院
        collegeDao.insert(name);
    }

    @Override
    public void deleteById(Integer id) {
        if (!majorDao.listAllByCollegeId(id).isEmpty()) {
            throw new HealthCodeException("请先删除该学院所有专业");
        }
        if (!teacherDao.listAllByCollegeId(id).isEmpty()) {
            throw new HealthCodeException("请先删除该学院所有教师");
        }
        collegeDao.deleteById(id);
    }
}
