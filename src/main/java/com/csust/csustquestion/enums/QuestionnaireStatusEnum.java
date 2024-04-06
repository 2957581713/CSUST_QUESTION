package com.csust.csustquestion.enums;

public enum QuestionnaireStatusEnum {


    PUBLISH("发布"),
    STATING("暂存"),
    FINISHES("已结束"),
    LAPSE("已失效");

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "QuestionnaireStatusEnum{" +
                "status='" + status + '\'' +
                '}';
    }

    QuestionnaireStatusEnum(String status) {
        this.status = status;
    }

    QuestionnaireStatusEnum() {
    }
}
