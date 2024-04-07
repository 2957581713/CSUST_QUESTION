package com.csust.csustquestion.service;

import com.csust.csustquestion.domain.Teacher;

public interface TeacherService {
    Teacher getById(Long id);

    Long getTeacherId(String openId,Long questionnaireId);
}
