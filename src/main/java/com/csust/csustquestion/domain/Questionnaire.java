package com.csust.csustquestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionnaire {
  private Long id;

  
  private String questionnaireName;
  
  private String questionnaireTarget;
  
  private String questionnaireStatus;

  private String description;

}
