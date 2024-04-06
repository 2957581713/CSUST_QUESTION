package com.csust.csustquestion.enums;

public enum SurveyStatusEnum {
    PUBLISH("0","发布"),
    STATING("1","暂存"),
    FINISHES("2","已结束"),
    LAPSE("3","已失效")

    ;


    private String code;
    private String desc;

    SurveyStatusEnum() {
    }

    SurveyStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SurveyStatusEnum{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
