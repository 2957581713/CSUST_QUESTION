package com.csust.csustquestion.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebUserVo {

  @NotBlank(message = "密码不能为空")
  private String userpassword;

  @NotBlank(message = "账号不能为空")
  private String userId;

}
