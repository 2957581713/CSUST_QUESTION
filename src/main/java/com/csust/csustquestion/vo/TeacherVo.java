package com.csust.csustquestion.vo;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class TeacherVo {
  String sex;
  
  String campus;
  
  Timestamp createDate;
  
  String optionContent;
  
  String questionDescription;
  
  String sort;

}
