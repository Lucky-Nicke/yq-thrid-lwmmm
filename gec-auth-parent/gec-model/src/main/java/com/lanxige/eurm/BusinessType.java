package com.lanxige.eurm;

/**
 * 业务类型枚举类
 */
public enum BusinessType {

    OTHER("OTHER"),
    QUERY("QUERY"),
    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String code;

    BusinessType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
