package com.csust.csustquestion.service.impl;

import com.csust.csustquestion.domain.Teacher;
import com.csust.csustquestion.mapper.TeacherMapper;
import com.csust.csustquestion.service.TeacherService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;


    @Override
    public Teacher getById(Long id) {
        return teacherMapper.getById(id);
    }

    @Override
    public Long getTeacherId(String openId,Long questionnaireId) {
        return teacherMapper.getIdByOpenId(openId,questionnaireId);
    }

    @Override
    public void add(Teacher teacher) {
        teacherMapper.insert(teacher);
    }
}
