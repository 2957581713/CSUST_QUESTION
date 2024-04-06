package com.csust.csustquestion.enums;

public enum BusinessExceptionEnums {
    CREATE_EXIST("存在未失效的同名问卷!")
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
