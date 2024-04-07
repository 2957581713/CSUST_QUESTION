package com.csust.csustquestion.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.csust.csustquestion.domain.Relation;
import com.csust.csustquestion.enums.BusinessExceptionEnums;
import com.csust.csustquestion.exception.BusinessException;
import com.csust.csustquestion.mapper.RelationMapper;
import com.csust.csustquestion.mapper.StudentMapper;
import com.csust.csustquestion.mapper.TeacherMapper;
import com.csust.csustquestion.service.RelationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {

    @Resource
    private RelationMapper relationMapper;

    @Resource
    private StudentMapper studentMapper;

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public Integer countByOptionId(Long optionId) {
        return countStudentByCondition(optionId,null,null,null,null,null);
    }

    @Override
    public Integer countByOptionIdAndStudentGrade(Long optionId, Long questionnaireId, String grade) {
//        List<Long> studentIds = studentMapper.getStudentIdByGradeAndQuestionnaireId(grade,questionnaireId);
////        return relationMapper.countByOptionIdAndStudentIdList(studentIds,optionId);
//
//
//        int num = 0;
//        int model = 1000;
//        int i = studentIds.size() / model;
//        int k = studentIds.size() % model;
//        int j = 0;
//        for (; j < i; j++) {
//            num += relationMapper.countByOptionIdAndStudentIdList(studentIds.subList(j*model,j*model+model),optionId);
//        }
//        num += relationMapper.countByOptionIdAndStudentIdList(studentIds.subList(j*model,j*model+k),optionId);
        return countStudentByCondition(optionId,questionnaireId,grade,null,null,null);
    }

    @Override
    public Integer countByStundentSex(Long optionId, String sex, Long questionnaireId) {

        return countStudentByCondition(optionId,questionnaireId,null,sex,null,null);
    }

    @Override
    public Integer countByStudentCampus(Long optionId, String campus, Long questionnaireId) {
        return countStudentByCondition(optionId,questionnaireId,null,null,campus,null);
    }

    @Override
    public Integer countByStudentAcademy(Long optionId, String academy, Long questionnaireId) {

        return countStudentByCondition(optionId,questionnaireId,null,null,null,academy);
    }

    @Override
    public Integer countByTeacherSort(Long optionId, String sort, Long questionnaireId) {
        return countTeacherByCondition(optionId,questionnaireId,sort,
                null,null);
    }

    @Override
    public Integer countByTeacherSex(Long optionId, String sex, Long questionnaireId) {
        return countTeacherByCondition(optionId,questionnaireId,null,sex,null);
    }

    @Override
    public Integer countByTeacherCampus(Long optionId, String campus, Long questionnaireId) {
        return countTeacherByCondition(optionId,questionnaireId,null,null,campus);
    }

    @Override
    public List<Relation> getByOptionId(Long optionId) {
        return relationMapper.getByOptionId(optionId);
    }

    @Override
    public void addRelations(List<Relation> relations) {
        if(CollUtil.isEmpty(relations)) throw new BusinessException(BusinessExceptionEnums.QUESTIONNAIRENOTCOMLETED.getMessage());
        relationMapper.insertList(relations);
    }

    private Integer countTeacherByCondition(Long optionId,Long questionnaireId, String sort, String sex,String campus) {
        List<Long> teacherIds = null;
        if(sort != null) teacherIds = teacherMapper.getTeacherIdsBySortAndQuestionnaireId(sort,questionnaireId);
        else if(sex != null) teacherIds = teacherMapper.getTeacherIdsBySexAndQuestionnaireId(sex,questionnaireId);
        else if(campus != null) teacherIds = teacherMapper.getTeacherIdsByCampusAndQuestionnaireId(campus,questionnaireId);
        if(CollUtil.isEmpty(teacherIds)) return 0;
        int num = 0;
        int model = 1000;
        int i = teacherIds.size() / model;
        int k = teacherIds.size() % model;
        int j = 0;
        for(;j<i;j++){
            num   += relationMapper.countByOptionIdAndTeacherIdList(teacherIds.subList(j*model,j*model+model),optionId);
        }
        num   += relationMapper.countByOptionIdAndTeacherIdList(teacherIds.subList(j*model,j*model+k),optionId);
        return num;
    }



    private Integer countStudentByCondition(Long optionId, Long questionnaireId, String grade,String sex,String campus,
                                     String academy) {
        if(questionnaireId == null ) return relationMapper.countByOptionId(optionId);
//        return relationMapper.countByOptionIdAndStudentIdList(studentIds,optionId);
        List<Long> studentIds = null;
        if(grade != null) studentIds = studentMapper.getStudentIdByGradeAndQuestionnaireId(grade,questionnaireId);
        else if(sex != null) studentIds = studentMapper.getStudentIdBySexAndQuestionnaireId(sex,questionnaireId);
        else if(campus != null ) studentIds = studentMapper.getStudentIdByCampusAndQuestionnaireId(campus,questionnaireId);
        else if(academy != null) studentIds = studentMapper.getStudentIdByAcademyAdnQuestionnaired(academy,questionnaireId);
        int num = 0;
        int model = 1000;
        if(CollUtil.isEmpty(studentIds)) return 0;
        int i = studentIds.size() / model;
        int k = studentIds.size() % model;
        int j = 0;
        for (; j < i; j++) {
            num += relationMapper.countByOptionIdAndStudentIdList(studentIds.subList(j*model,j*model+model),optionId);
        }
        num += relationMapper.countByOptionIdAndStudentIdList(studentIds.subList(j*model,j*model+k),optionId);
        return num;
    }
}
