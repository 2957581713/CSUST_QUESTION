package com.csust.csustquestion.service;

import com.csust.csustquestion.vo.QuestionOptionVo;
import com.csust.csustquestion.vo.Vo;

public interface UserService {
    String login(Vo vo);

    void save(QuestionOptionVo questionOptionVo);
}
