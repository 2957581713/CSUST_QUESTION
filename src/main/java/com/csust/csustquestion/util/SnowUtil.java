package com.csust.csustquestion.util;

import cn.hutool.core.util.IdUtil;

public class SnowUtil {

    private static long dataCenterId = 1;
    private static long workerId = 1;

    public static long getSnowflakeNextId(){return IdUtil.getSnowflake(workerId,dataCenterId).nextId();
    }

    public static String getSnowflakeNextStr(){return IdUtil.getSnowflake(workerId,dataCenterId).nextIdStr();}

}
