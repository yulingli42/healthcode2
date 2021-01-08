package com.healthcode.service.impl;

import com.google.common.collect.Lists;
import com.healthcode.domain.College;
import com.healthcode.service.ICollegeService;

import java.util.List;

public class CollegeServiceImpl implements ICollegeService {
    @Override
    public List<College> getAllCollege() {
        //TODO
        College college = new College();
        college.setId(1);
        college.setName("test");

        return Lists.newArrayList(college);
    }

    @Override
    public College getCollegeById(Integer id) {
        //TODO
        College college = new College();
        college.setId(1);
        college.setName("test");
        return college;
    }
}
