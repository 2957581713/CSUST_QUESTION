package com.csust.csustquestion.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
  private Long id;
  
  private String userName;
  
  private Timestamp createDate;
  
  private String openId;
  
  private String avatar;

}
