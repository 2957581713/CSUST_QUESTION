package com.csust.csustquestion.mapper;


import com.csust.csustquestion.domain.WebUser;

public interface WebUserMapper {


    WebUser getByUserId(String userId);
}
