package com.jebytek.server.enums;

public enum SectionChargeEnum {

    CHARGE("C", "Charge"),
    FREE("F", "Free");

    private String code;

    private String desc;

    SectionChargeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
