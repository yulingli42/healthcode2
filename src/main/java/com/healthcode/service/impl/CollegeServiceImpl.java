package com.healthcode.service.impl;

import com.healthcode.dao.CollegeDao;
import com.healthcode.domain.College;
import com.healthcode.service.ICollegeService;

import java.util.List;

/**
 * @author zhenghong
 */
public class CollegeServiceImpl implements ICollegeService {
    private final CollegeDao collegeDao = new CollegeDao();
    @Override
    public List<College> getAllCollege() {
        //获取全部学院信息
        return collegeDao.listAll();
    }

    @Override
    public College getCollegeById(Integer id) {
        //获取单个学院信息
        return collegeDao.getById(id);
    }

    @Override
    public void addCollege(String name) {
        System.out.println("name:" + name);
    }
}
