package com.csust.csustquestion.mapper;

import com.csust.csustquestion.domain.Option;

import java.util.List;

public interface OptionMapper {
    List<Option> getQuestionOption(Long questionId);

    void insertList(List<Option> optionList);

    Long getIdByQuestionId(Long questionId);
}
