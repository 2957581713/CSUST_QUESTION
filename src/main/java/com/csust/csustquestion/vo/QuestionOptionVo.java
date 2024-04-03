package com.csust.csustquestion.vo;

import lombok.Data;

@Data
public class QuestionOptionVo {
  private String[] basicInfo;
  
  private String questionnaireName;
  
  private String[][] questionAnswer;
  
  private String open_id;


  

}
