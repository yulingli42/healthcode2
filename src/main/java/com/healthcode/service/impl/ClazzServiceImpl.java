package com.healthcode.service.impl;

import com.google.common.collect.Lists;
import com.healthcode.domain.Clazz;
import com.healthcode.service.IClazzService;

import java.util.List;

public class ClazzServiceImpl implements IClazzService {
    @Override
    public List<Clazz> getAllClazzByMajor(Integer majorId) {
        //TODO
        Clazz clazz = new Clazz();
        clazz.setId(1);
        clazz.setName("test");
        return Lists.newArrayList(clazz);
    }
}
