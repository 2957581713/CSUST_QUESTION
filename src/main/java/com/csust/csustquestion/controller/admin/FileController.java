package com.csust.csustquestion.controller.admin;

import cn.hutool.core.collection.CollUtil;
import com.csust.csustquestion.domain.*;
import com.csust.csustquestion.enums.BusinessExceptionEnums;
import com.csust.csustquestion.enums.QuestionTypeEnum;
import com.csust.csustquestion.exception.BusinessException;
import com.csust.csustquestion.service.*;
import com.csust.csustquestion.vo.StudentVo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

@RestController
public class FileController {

    @Resource
    private QuestionnaireService questionnaireService;

    @Resource
    private SurveyService surveyService;
    @Resource
    private StudentService studentService;
    @Resource
    private QuestionService questionService;
    @Resource
    private OptionService optionService;
    @Resource
    private RelationService relationService;


    @RequestMapping({"questionnaire/studentexcel"})
    public void writeStudentExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("questionnaireName") String questionnaireName) throws Exception {
        List<StudentVo> studentVos = findAllStudentVo(questionnaireName);
        HSSFWorkbook hw = student(studentVos, questionnaireName);
        toExcel(response, hw, "student");
    }



    public HSSFWorkbook student(List<StudentVo> studentVos, String questionnaireName) {
        HSSFWorkbook wk = new HSSFWorkbook();
        HSSFSheet sheet = wk.createSheet("问卷名------" + questionnaireName + "--------学生意见建议表");
        HSSFRow smallTitle = sheet.createRow(0);
        smallTitle.createCell(0).setCellValue("校区");
        smallTitle.createCell(1).setCellValue("年级");
        smallTitle.createCell(2).setCellValue("性别");
        smallTitle.createCell(3).setCellValue("学院");
        smallTitle.createCell(4).setCellValue("回答问题的时间");
        smallTitle.createCell(5).setCellValue("问题");
        smallTitle.createCell(6).setCellValue("回答的答案");
        int count = 1;
        for (StudentVo studentVo : studentVos) {
            HSSFRow row = sheet.createRow(count++);
            row.createCell(0).setCellValue(studentVo.getCampus());
            row.createCell(1).setCellValue(studentVo.getGarde());
            row.createCell(2).setCellValue(studentVo.getSex());
            row.createCell(3).setCellValue(studentVo.getAcademy());
            row.createCell(4).setCellValue(studentVo.getCreateDate().toString());
            row.createCell(5).setCellValue(studentVo.getQuestionDescription());
            row.createCell(6).setCellValue(studentVo.getOptionContent());
        }
        return wk;
    }

    public void toExcel(HttpServletResponse response, HSSFWorkbook wk, String name) throws Exception {
        String fileName = name + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xls";
        try {
            setResponseHeader(response, fileName);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            wk.write((OutputStream)servletOutputStream);
            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setResponseHeader(HttpServletResponse response, String fileName) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=" +
                URLEncoder.encode(fileName, "UTF-8"));
    }

    public List<StudentVo> findAllStudentVo(String questionnaireName) {
        Questionnaire questionnaire = questionnaireService.getQuestionnaireByName(questionnaireName);
        Long questionnaireId = questionnaire.getId();
        List<Long> surveyIdList = surveyService.getIdsByQuestionId(questionnaireId);
        if(CollUtil.isEmpty(surveyIdList)) throw new BusinessException(BusinessExceptionEnums.EXCEPTION.getMessage());
        List<Question> questionList = questionService.getTypeQuestionInSurveyId(surveyIdList, QuestionTypeEnum.BLANK.getDesc());
        if(CollUtil.isEmpty(questionList)) throw new BusinessException(BusinessExceptionEnums.EXCEPTION.getMessage());
        List<StudentVo> studentVoList = new ArrayList<>();
        for (Question question :
                questionList) {
            Long questionId = question.getId();
            String questionDescription = question.getQuestionDescription();
//            找到填空题id，理论上自然一个题目只有一个填空选项
            Long optionId = optionService.getIdByQuestionId(questionId);
            List<Relation> relationList = relationService.getByOptionId(optionId);
            for (Relation relation :
                    relationList) {
                Long userId = relation.getUserId();
                Student student = studentService.getById(userId);
                if(student == null) {
                    System.out.println();
                }
                StudentVo studentVo = new StudentVo();
                studentVo.setCampus(student.getCampus());
                studentVo.setGarde(student.getGrade());
                studentVo.setSex(student.getSex());
                studentVo.setAcademy(student.getAcademyName());
                studentVo.setCreateDate(student.getCreateDate());
                studentVo.setOptionContent(relation.getOptionContent());
                studentVo.setQuestionDescription(questionDescription);
                studentVoList.add(studentVo);


            }

        }
        return studentVoList;
    }


}
