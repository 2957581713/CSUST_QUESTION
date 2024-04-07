package com.csust.csustquestion.enums;

public enum TargetEnum {
    TEACHER("teacher"),
    STUDENT("student");


    private String target;

    TargetEnum(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    TargetEnum() {
    }
}
