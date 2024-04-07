package com.csust.csustquestion.service;

import com.csust.csustquestion.domain.Relation;

import java.util.List;

public interface RelationService {
    Integer countByOptionId(Long optionId);

    Integer countByOptionIdAndStudentGrade(Long optionId, Long questionnaireId, String grade);

    Integer countByStundentSex(Long optionId, String sex, Long questionnaireId);

    Integer countByStudentCampus(Long optionId, String campus, Long questionnaireId);

    Integer countByStudentAcademy(Long optionId, String academy, Long questionnaireId);

    Integer countByTeacherSort(Long optionId, String sort, Long questionnaireId);

    Integer countByTeacherSex(Long optionId, String sex, Long questionnaireId);

    Integer countByTeacherCampus(Long optionId, String campus, Long questionnaireId);

    List<Relation> getByOptionId(Long optionId);
}
