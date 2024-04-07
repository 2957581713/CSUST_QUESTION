package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Option;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OptionMapper {
    List<Option> getQuestionOption(Long questionId);

    void insertList(List<Option> optionList);

    Long getIdByQuestionId(Long questionId);

    Long getIdByOptionNameAndQuestionId(@Param("questionId") Long questionId,@Param("optionName") String optionName);
}
