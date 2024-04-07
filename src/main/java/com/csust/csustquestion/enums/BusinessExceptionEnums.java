package com.csust.csustquestion.enums;

public enum BusinessExceptionEnums {
    CREATE_EXIST("存在未失效的同名问卷!"),
    EXCEPTION("系统异常，请联系管理员")
    ;


    private String message;


    BusinessExceptionEnums() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    BusinessExceptionEnums(String message) {
        this.message = message;
    }
}
