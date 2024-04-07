package com.csust.csustquestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Survey implements Serializable {
  private Long id;
  
  private String surveyName;
  
  private String questionnaireName;

  
  private Timestamp createDate;
  
  private Timestamp updateDate;

  private Long questionnaireId;
  private Integer sort;
}
