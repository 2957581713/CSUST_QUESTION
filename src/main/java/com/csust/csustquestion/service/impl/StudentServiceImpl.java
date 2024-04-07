package com.csust.csustquestion.service.impl;

import com.csust.csustquestion.domain.Student;
import com.csust.csustquestion.mapper.StudentMapper;
import com.csust.csustquestion.service.StudentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    private StudentMapper studentMapper;


    @Override
    public Student getById(Long studentId) {
        return studentMapper.getById(studentId);
    }

    @Override
    public Long getStudentId(String openId, Long questionnaireId) {
        return studentMapper.getIdByOpenIdAndQuestionnaireId(openId,questionnaireId);
    }
}
