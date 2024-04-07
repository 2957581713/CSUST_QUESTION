package com.csust.csustquestion.enums;

public enum QuestionTypeEnum {
    OPTION("单选"),
    OPTIONS("多选"),
    BLANK("填空"),

    ;

    private String desc;

    @Override
    public String toString() {
        return "QuestionTypeEnum{" +
                "desc='" + desc + '\'' +
                '}';
    }

    QuestionTypeEnum(String desc) {
        this.desc = desc;
    }

    QuestionTypeEnum() {
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
