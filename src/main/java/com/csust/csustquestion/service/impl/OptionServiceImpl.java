package com.csust.csustquestion.service.impl;

import com.csust.csustquestion.domain.Option;
import com.csust.csustquestion.mapper.OptionMapper;
import com.csust.csustquestion.service.OptionService;
import com.csust.csustquestion.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    @Resource
    private OptionMapper optionMapper;


    @Override
    public String[] getOptionNameByQuestionId(Long questionId) {
        List<Option> optionList = optionMapper.getQuestionOption(questionId);
        String[] optionNames = new String[optionList.size()];
        for (int i = 0; i < optionList.size(); i++) {
            optionNames[i] = optionList.get(i).getOptionName();
        }
        return optionNames;
    }

    @Override
    public void addOption(long questionId, String[] options) {
        List<Option> optionList = new ArrayList<>();
        for (int i = 0; i < options.length; i++) {
            Option option = new Option();
            option.setId(SnowUtil.getSnowflakeNextId());
            option.setQuestionId(questionId);
            option.setOptionName(options[i]);
            optionList.add(option);
        }
        return;
    }
}
