package com.healthcode.service.impl;

import com.healthcode.dao.MajorDao;
import com.healthcode.domain.Major;
import com.healthcode.service.IMajorService;

import java.util.List;

/**
 * @author zhenghong
 */
public class MajorServiceImpl implements IMajorService {
    private final MajorDao majorDao = new MajorDao();
    @Override
    public List<Major> getAllMajorByCollegeId(Integer collegeId) {
        //根据学院号获取所有专业
        return majorDao.listAll(collegeId);
    }

    @Override
    public void addMajor(Integer collegeId, String name) {

        System.out.println("collegeId:" + collegeId);
        System.out.println("name:" + name);
        // TODO
    }
}
