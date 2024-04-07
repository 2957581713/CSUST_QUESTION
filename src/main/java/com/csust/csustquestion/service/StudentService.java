package com.csust.csustquestion.service;

import com.csust.csustquestion.domain.Student;

public interface StudentService {
    Student getById(Long studentId);

    Long getStudentId(String openId, Long questionnaireId);
}
