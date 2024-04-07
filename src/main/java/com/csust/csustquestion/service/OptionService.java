package com.csust.csustquestion.service;

public interface OptionService {

    String[] getOptionNameByQuestionId(Long questionId);


    void addOption(long questionId, String[] option);

    Long getIdByQuestionId(Long questionId);

    Long getIdByOptionNameAndQuestionId(Long questionId, String optionName);
}




