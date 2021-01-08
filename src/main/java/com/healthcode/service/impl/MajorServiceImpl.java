package com.healthcode.service.impl;

import com.google.common.collect.Lists;
import com.healthcode.domain.Major;
import com.healthcode.service.IMajorService;

import java.util.List;

/**
 * @author qianlei
 */
public class MajorServiceImpl implements IMajorService {
    @Override
    public List<Major> getAllMajorByCollegeId(Integer collegeId) {
        //TODO

        Major major = new Major();
        major.setId(1);
        major.setName("test");
        return Lists.newArrayList(major);
    }
}
