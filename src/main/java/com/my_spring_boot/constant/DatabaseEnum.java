package com.my_spring_boot.constant;

/**
 * @author JiHC
 * @date 2020/9/20
 */
public enum DatabaseEnum {

    MAN("男"),
    WOMAN("女"),
    UNKNOWN("未知"),
    NOT_DELETE("未删除"),
    IS_DELETE("已删除"),
    ADMIN("admin"),
    USER("user");

    private final String value;

    DatabaseEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
