package com.csust.csustquestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question implements Serializable {
  private Long id;
  
  private Long surveyId;
  
  private String questionType;
  
  private String questionDescription;
  
  private Integer questionSort;
  
  private String requiredFlag;

}
