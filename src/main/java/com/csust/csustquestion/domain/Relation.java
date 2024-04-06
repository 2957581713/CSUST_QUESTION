package com.csust.csustquestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation implements Serializable {
  private Long id;
  private Long userId;
  
  private Long optionId;
  
  private String optionContent;
  
  private String userStatus;

  public Relation(Long userId, Long optionId, String optionContent, String userStatus) {
    this.userId = userId;
    this.optionId = optionId;
    this.optionContent = optionContent;
    this.userStatus = userStatus;
  }
}
