package com.csust.csustquestion.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.csust.csustquestion.domain.WebUser;
import com.csust.csustquestion.mapper.WebUserMapper;
import com.csust.csustquestion.service.WebUserService;
import com.csust.csustquestion.vo.WebUserVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class WebUserServiceImpl implements WebUserService {

    @Resource
    private WebUserMapper webUserMapper;

    @Override
    public boolean login(WebUserVo req) {
        WebUser webUser = webUserMapper.getByUserId(req.getUserId());
        if(BeanUtil.isNotEmpty(webUser)){
            if(webUser.getUserpassword().equals(req.getUserpassword())) return true;
        }
        return false;
    }
}
