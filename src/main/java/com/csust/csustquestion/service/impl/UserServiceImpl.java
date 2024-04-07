package com.csust.csustquestion.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.csust.csustquestion.domain.*;
import com.csust.csustquestion.enums.BusinessExceptionEnums;
import com.csust.csustquestion.enums.QuestionTypeEnum;
import com.csust.csustquestion.enums.TargetEnum;
import com.csust.csustquestion.exception.BusinessException;
import com.csust.csustquestion.mapper.UserMapper;
import com.csust.csustquestion.service.*;
import com.csust.csustquestion.util.Base64Utils;
import com.csust.csustquestion.util.SnowUtil;
import com.csust.csustquestion.util.WeChatUtil;
import com.csust.csustquestion.vo.QuestionOptionVo;
import com.csust.csustquestion.vo.Vo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionnaireService questionnaireService;
    @Resource
    private StudentService studentService;
    @Resource
    private SurveyService surveyService;
    @Resource
    private QuestionService questionService;

    @Resource
    private OptionService optionService;
    @Resource
    private RelationService relationService;

    @Resource
    private TeacherService teacherService;

    private static final String openIdS = "openid";
    private static final String sessionKeyS = "session_key";
    @Override
    public String login(Vo vo) {
        JSONObject sessionKeyOrOpenId = WeChatUtil.getSessionKeyOrOpenId(vo.getCode());
        String openId = sessionKeyOrOpenId.getString(openIdS);
        String sessionKey = sessionKeyOrOpenId.getString(sessionKeyS);
        System.out.println("aaaaaaaaa");
        List<User> userList = userMapper.getByopenId(openId);
        if(CollUtil.isEmpty(userList)){
            User user = new User();
            user.setId(SnowUtil.getSnowflakeNextId());
            user.setUserName(null);
            user.setCreateDate(null);
            user.setOpenId(openId);
            user.setAvatar(null);

            userMapper.addUser(user);
        }
        return openId;
    }

    @Override
    @Transactional
    public void save(QuestionOptionVo questionOptionVo) {
        String[] basicInfo = questionOptionVo.getBasicInfo();
        String questionnaireName = questionOptionVo.getQuestionnaireName();
        Questionnaire questionnaire = questionnaireService.getQuestionnaireByName(questionnaireName);
        String[][] questionAnswer = questionOptionVo.getQuestionAnswer();
        String openId = Base64Utils.decode(questionOptionVo.getOpen_id());
        Timestamp now = DateTime.now().toTimestamp();
        List<Relation> relations = new ArrayList<>();
//        填表人基本信息
        String userType = basicInfo[0];
//        保存学生填表信息
        if(userType.equals(TargetEnum.STUDENT.getTarget())){

            String sex = basicInfo[1];
            String grade = basicInfo[2];
            String campus = basicInfo[3];
            String academyName = basicInfo[4];
            Student student = new Student();
            long userId = SnowUtil.getSnowflakeNextId();
            student.setId(userId);
            student.setCampus(campus);
            student.setGrade(grade);
            student.setSex(sex);
            student.setAcademyName(academyName);
            student.setCreateDate(now);
            student.setOpenId(openId);
            student.setQuestionniareName(questionnaire.getQuestionnaireName());
            student.setQuestionniareId(questionnaire.getId());
            Long studentId = studentService.getStudentId(openId, questionnaire.getId());
            if(studentId != null) throw new BusinessException(BusinessExceptionEnums.QUESTIONNAIRECOMLETED.getMessage());
            studentService.add(student);
            List<Long> surveyIds = surveyService.getIdsByQuestionId(questionnaire.getId());
            setAnswer(questionAnswer, relations, userId, surveyIds);

        }
//        保存老师填表信息
        else if(userType.equals(TargetEnum.TEACHER.getTarget())){
            Teacher teacher = new Teacher();
            String sex = basicInfo[1];
            String campus = basicInfo[2];
            String sort = basicInfo[3];
            long userId = SnowUtil.getSnowflakeNextId();
            teacher.setId(userId);
            teacher.setSex(sex);
            teacher.setCreateDate(now);
            teacher.setCampus(campus);
            teacher.setOpenId(openId);
            teacher.setQuestionnaireName(questionnaireName);
            teacher.setSort(sort);
            teacher.setQuestionniareId(questionnaire.getId());
            Long teacherId = teacherService.getTeacherId(openId, questionnaire.getId());
            if(teacherId != null) throw new BusinessException(BusinessExceptionEnums.QUESTIONNAIRECOMLETED.getMessage());
            teacherService.add(teacher);
            List<Long> surveyIds = surveyService.getIdsByQuestionId(questionnaire.getId());
            setAnswer(questionAnswer, relations, userId, surveyIds);
        }
//        if(true) throw new RuntimeException();

        relationService.addRelations(relations);
    }

    private void setAnswer(String[][] questionAnswer, List<Relation> relations, long userId, List<Long> surveyIds) {
        for (int i = 0; i < surveyIds.size(); i++) {
            Long surveyId = surveyIds.get(i);
            List<Question> questionList = questionService.getBySurveyId(surveyId);
            for (int j = 0; j < questionList.size(); j++) {
                Question question = questionList.get(j);
                Long questionId = question.getId();
                String[] oldstr = questionAnswer[i][j].split("answer:");
                String type = oldstr[0].substring(5);
                if(type.equals(QuestionTypeEnum.OPTION.getDesc())){
                    Long optionId = optionService.getIdByOptionNameAndQuestionId(questionId,oldstr[1]);
                    relations.add(new Relation(SnowUtil.getSnowflakeNextId(),
                            userId,optionId,"",TargetEnum.STUDENT.getTarget()));
                }
                else if(type.equals(QuestionTypeEnum.OPTIONS.getDesc())){
                    String[] newStr = oldstr[1].split("&&");
                    for(String s : newStr){
                        Long optionId = optionService.getIdByOptionNameAndQuestionId(questionId,s);
                        relations.add(new Relation(SnowUtil.getSnowflakeNextId(),
                                userId,optionId,"",TargetEnum.STUDENT.getTarget()));
                    }
                }else if(type.equals(QuestionTypeEnum.BLANK.getDesc())){
                    Long optionId = optionService.getIdByQuestionId(questionId);
                    relations.add(new Relation(SnowUtil.getSnowflakeNextId(),
                            userId,optionId,oldstr[1],TargetEnum.STUDENT.getTarget()));
                }


            }
        }
    }

}
