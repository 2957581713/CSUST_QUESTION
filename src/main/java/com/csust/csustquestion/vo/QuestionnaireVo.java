package com.csust.csustquestion.vo;

import lombok.Data;
@Data
public class QuestionnaireVo {
  private String questionnaireName;
  
  private String questionnaireTarget;
  
  private String description;
  
  private String[] surveyName;
  
  private String[][] questionOption;
  

}
