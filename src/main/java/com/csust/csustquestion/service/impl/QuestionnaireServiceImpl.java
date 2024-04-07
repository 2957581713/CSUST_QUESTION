package com.csust.csustquestion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import com.csust.csustquestion.domain.Option;
import com.csust.csustquestion.domain.Question;
import com.csust.csustquestion.domain.Questionnaire;
import com.csust.csustquestion.domain.Survey;
import com.csust.csustquestion.enums.*;
import com.csust.csustquestion.exception.BusinessException;
import com.csust.csustquestion.mapper.*;
import com.csust.csustquestion.service.OptionService;
import com.csust.csustquestion.service.QuestionnaireService;
import com.csust.csustquestion.service.RelationService;
import com.csust.csustquestion.util.SnowUtil;
import com.csust.csustquestion.vo.QuestionnaireVo;
import com.csust.csustquestion.vo.ResultVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Resource
    private QuestionnaireMapper questionnaireMapper;

    @Resource
    private SurveyMapper surveyMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private OptionMapper optionMapper;
    @Resource
    private OptionService optionService;

    @Resource
    private RelationService relationService;




    @Override
    public String[] getQuestionAndStatus() {
//        查出未失效的问卷
        List<Questionnaire> questionnaires = questionnaireMapper.selectNotDelete();
        String[] result = new String[questionnaires.size()];
        for (int i = 0; i < questionnaires.size(); i++) {
            Questionnaire questionnaire = questionnaires.get(i);
            result[i] = "name:"+questionnaire.getQuestionnaireName()+"<>status:"+
                    questionnaire.getQuestionnaireStatus()+"<>target:"+questionnaire.getQuestionnaireTarget();
        }
        return result;
    }

// TODO 需要对接前端，保证前端传过来的应该是id而不是名字
    @Override
    public QuestionnaireVo getQuestionnaire(String questionnaireName) {
        QuestionnaireVo questionnaireVo = new QuestionnaireVo();
        questionnaireVo.setQuestionnaireName(questionnaireName);
//        按名字获得问卷（未失效的）
        Questionnaire questionnaire = questionnaireMapper.selectByName(questionnaireName);
        questionnaireVo.setQuestionnaireTarget(questionnaire.getQuestionnaireTarget());
        questionnaireVo.setDescription(questionnaire.getDescription());
        List<Survey> surveyList = surveyMapper.getByQuestionId(questionnaire.getId());
        String[] surveyNames = new String[surveyList.size()];
//        List<String> surveyNameList = surveyMapper.getByQuestionId(questionnaire.getId());
//        String[] surveyNames = surveyNameList.toArray(new String[surveyNameList.size()]);
//        现在默认每个模块只能添加十个问题（ TODO ） 后面对接前端，前端应该是接收List《》,对象里包含surveyNames和其对应的问题链表
        String[][] questionOption = new String[surveyList.size()][10];
        for (int i = 0; i < surveyList.size(); i++) {
            Survey survey = surveyList.get(i);
            surveyNames[i] = survey.getSurveyName();
            List<Question> questions = questionMapper.selectBySurveyId(survey.getId());
            for (int j = 0; j < questions.size(); j++) {
                StringBuilder sd = new StringBuilder();
                Question question = questions.get(j);
                sd.append("question:");
                sd.append(question.getQuestionDescription());
                sd.append("type:");
                sd.append(question.getQuestionType());
                if(!question.getQuestionType().equals(QuestionTypeEnum.BLANK.getDesc())){
                    sd.append("option:");
                    String[] options = optionService.getOptionNameByQuestionId(question.getId());
                    for (int s = 0; s < options.length; s++) {
                        sd.append(options[s]);
                        if(s == options.length - 1) break;
                        sd.append("&&");
                    }
                }
                questionOption[i][j] = sd.toString();
            }
        }
        questionnaireVo.setQuestionOption(questionOption);
        questionnaireVo.setSurveyName(surveyNames);
        return questionnaireVo;
    }

    @Override
    public Questionnaire getQuestionnaireByName(String questionnaireName) {
        return questionnaireMapper.selectByName(questionnaireName);
    }

    // TODO 后面加入修改的功能
//    添加涉及到的表有：问卷，问题，模块，选项
    @Override
    @Transactional
    public void create(QuestionnaireVo questionnaireVo) {
//        先校验是否存在同名的未失效的问卷
        Questionnaire questionnaire1 = questionnaireMapper.selectByName(questionnaireVo.getQuestionnaireName());
        if(BeanUtil.isNotEmpty(questionnaire1)){
            throw new BusinessException(BusinessExceptionEnums.CREATE_EXIST.getMessage());
        }


        Questionnaire questionnaire = new Questionnaire();
        Long questionnaireId = SnowUtil.getSnowflakeNextId();
        questionnaire.setId(questionnaireId);
        String questionnaireName = questionnaireVo.getQuestionnaireName();
        questionnaire.setQuestionnaireName(questionnaireName);
        questionnaire.setQuestionnaireTarget(questionnaireVo.getQuestionnaireTarget());
        questionnaire.setQuestionnaireStatus(QuestionnaireStatusEnum.STATING.getStatus());
        questionnaire.setDescription(questionnaireVo.getDescription());
//        插入问卷
        questionnaireMapper.insert(questionnaire);
        DateTime now = DateTime.now();

        String[] surveyNames = questionnaireVo.getSurveyName();
        String[][] questionOptions = questionnaireVo.getQuestionOption();
        List<Survey> surveyList = new ArrayList<>();
        List<Question> questionList = new ArrayList<>();
        List<Option> optionList = new ArrayList<>();
        for (int i = 0; i < surveyNames.length; i++) {
            Survey survey = new Survey();
            Long surveyId = SnowUtil.getSnowflakeNextId();
            survey.setId(surveyId);
            survey.setSurveyName(surveyNames[i]);
            survey.setQuestionnaireName(questionnaireName);

            survey.setCreateDate(now.toTimestamp());
            survey.setUpdateDate(now.toTimestamp());
            survey.setQuestionnaireId(questionnaireId);
            survey.setSort(i);
            surveyList.add(survey);

            for (int j = 0; j < questionOptions[i].length; j++) {
                Question question = new Question();
                long questionId = SnowUtil.getSnowflakeNextId();
                String questionStr = questionOptions[i][j];
                String[] str1 = questionStr.split("type:");
                String strQuestion = str1[0].substring(9);
                String[] str2 = str1[1].split("option:");
                String strType = str2[0];

                question.setId(questionId);
                question.setSurveyId(surveyId);
                question.setQuestionType(strType);
                question.setQuestionDescription(strQuestion);
                question.setQuestionSort(j);
                question.setRequiredFlag("0");//0必填，1非必填
                questionList.add(question);
                if(!strType.equals(QuestionTypeEnum.BLANK.getDesc())){
                    String[] options = str2[1].split("&&");

                    for (int s = 0; s < options.length; s++) {
                        Option option = new Option();
                        option.setId(SnowUtil.getSnowflakeNextId());
                        option.setQuestionId(questionId);
                        option.setOptionName(options[s]);
                        optionList.add(option);
                    }
                }else {
                    Option option = new Option();
                    option.setId(SnowUtil.getSnowflakeNextId());
                    option.setQuestionId(questionId);
                    option.setOptionName("");
                    optionList.add(option);
                }
            }
        }
        // TODO
//        插入问题模块
        surveyMapper.insertList(surveyList);
//        插入问题选项
        optionMapper.insertList(optionList);
//        插入具体问题
        questionMapper.insertList(questionList);

        return;
    }

    @Override
    public void updateStatus(String questionnaireName, String questionnaireStatus) {
//        将其设置为失效 TODO 对接前端让他只传一个id过来删除
        questionnaireMapper.setLase(questionnaireName,questionnaireStatus);
    }

    @Override
    public String[] getQuestionAndStatus2() {
        List<Questionnaire> questionnaires = questionnaireMapper.getQuestionNameByStatus(QuestionnaireStatusEnum.PUBLISH.getStatus());
        String[] result = new String[questionnaires.size()];
        for (int i = 0; i < questionnaires.size(); i++) {
            Questionnaire questionnaire = questionnaires.get(i);
            result[i] = "name:"+questionnaire.getQuestionnaireName()+"<>status:"+
                    questionnaire.getQuestionnaireStatus()+"<>target:"+questionnaire.getQuestionnaireTarget();
        }
        return result;
    }

    @Override
    public ResultVo getResult(String questionnaireName) {
        return getResultByCondition(questionnaireName,null,null,null,null,null);
    }

    @Override
    public ResultVo getResultByGrade(String questionnaireName, String grade) {
        return getResultByCondition(questionnaireName,grade,null,null,null,null);
    }

    @Override
    public ResultVo getResultBySex(String questionnaireName, String sex) {
        return getResultByCondition(questionnaireName,null,sex,null,null,null);
    }

    @Override
    public ResultVo getResultByCampus(String questionnaireName, String campus) {
        return getResultByCondition(questionnaireName,null,null,campus,null,null);
    }

    @Override
    public ResultVo getResultByAcademyName(String questionnaireName, String academyName) {
        return getResultByCondition(questionnaireName,null,null,
                null,academyName,null);
    }

    @Override
    public ResultVo getResultBySort(String questionnaireName, String sort) {
        return getResultByCondition(questionnaireName,null,null,null,null,sort);
    }


    private ResultVo getResultByCondition(String questionnaireName, String grade,String sex,String campus,
                                          String academy,String sort) {
        ResultVo resultVo = new ResultVo();
        Questionnaire questionnaire = questionnaireMapper.selectByName(questionnaireName);
        Long questionnaireId = questionnaire.getId();
        String questionnaireTarget = questionnaire.getQuestionnaireTarget();
        List<Survey> surveyList = surveyMapper.getByQuestionName(questionnaireName);
        String[][] questionResult = new String[surveyList.size()][10];
        String[] surveyNames = new String[surveyList.size()];
        for (int i = 0; i < surveyList.size(); i++) {
            Survey survey = surveyList.get(i);
            surveyNames[i] = survey.getSurveyName();
            List<Question> questions = questionMapper.selectBySurveyId(survey.getId());
            for (int j = 0; j < questions.size(); j++) {
                Question question = questions.get(j);
                if(!question.getQuestionType().equals("填空")){
                    StringBuilder sd = new StringBuilder();
                    sd.append("question:");
                    sd.append(question.getQuestionDescription());
                    sd.append("type:");
                    sd.append(question.getQuestionType());
                    sd.append("option:");
                    List<Option> options = optionMapper.getQuestionOption(question.getId());
                    for (int s = 0; s < options.size(); s++) {
                        Option option = options.get(s);
                        sd.append(option.getOptionName());
                        if(s == options.size() - 1)break;
                        sd.append("&&");
                    }
                    sd.append("result:");
                    for (int x = 0; x < options.size(); x++) {
                        Option option = options.get(x);
                        Long optionId = option.getId();
//                        选择该选项人数
                        Integer num = 0;
                        if(grade != null)
                            num = relationService.countByOptionIdAndStudentGrade(optionId, questionnaireId,grade);
                        else if(sex != null) {
                            if(questionnaireTarget.equals(TargetEnum.STUDENT.getTarget()))num = relationService.countByStundentSex(optionId, sex, questionnaireId);
                            else num = relationService.countByTeacherSex(optionId, sex, questionnaireId);
                        }
                        else if(campus != null) {
                            if(questionnaireTarget.equals(TargetEnum.STUDENT.getTarget())) num = relationService.countByStudentCampus(optionId,campus, questionnaireId);
                            else num = relationService.countByTeacherCampus(optionId,campus, questionnaireId);
                        }
                        else if(academy != null){
                            num = relationService.countByStudentAcademy(optionId,academy,questionnaireId);
                        }
                        else if(sort != null){
                            if(questionnaireTarget.equals(TargetEnum.TEACHER.getTarget())){
                                num = relationService.countByTeacherSort(optionId,sort,questionnaireId);
                            }
                        }
                        else
                            num = relationService.countByOptionId(optionId);
                        sd.append(num);
                        if(x < options.size() - 1) sd.append("**");
                    }
                    questionResult[i][j] = sd.toString();
                }

            }
        }
        resultVo.setQuestionnaireName(questionnaireName);
        resultVo.setSurveyName(surveyNames);
        resultVo.setResultNum(questionResult);
        return resultVo;
    }
}
