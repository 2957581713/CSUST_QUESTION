package com.csust.csustquestion.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.csust.csustquestion.domain.User;
import com.csust.csustquestion.mapper.UserMapper;
import com.csust.csustquestion.service.UserService;
import com.csust.csustquestion.util.SnowUtil;
import com.csust.csustquestion.util.WeChatUtil;
import com.csust.csustquestion.vo.Vo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    private static final String openIdS = "openid";
    private static final String sessionKeyS = "session_key";
    @Override
    public String login(Vo vo) {
        JSONObject sessionKeyOrOpenId = WeChatUtil.getSessionKeyOrOpenId(vo.getCode());
        String openId = sessionKeyOrOpenId.getString(openIdS);
        String sessionKey = sessionKeyOrOpenId.getString(sessionKeyS);
        System.out.println("aaaaaaaaa");
        List<User> userList = userMapper.getByopenId(openId);
        if(CollUtil.isEmpty(userList)){
            User user = new User();
            user.setId(SnowUtil.getSnowflakeNextId());
            user.setUserName(null);
            user.setCreateDate(null);
            user.setOpenId(openId);
            user.setAvatar(null);

            userMapper.addUser(user);
        }
        return openId;
    }

}
