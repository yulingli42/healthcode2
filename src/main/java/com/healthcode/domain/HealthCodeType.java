package com.healthcode.domain;

/**
 * 健康码种类
 *
 * @author qianlei
 */
public enum HealthCodeType {
    /**
     * 绿码
     */
    GREEN("绿码"),
    /**
     * 黄码
     */
    YELLOW("黄码"),
    /**
     * 红码
     */
    RED("红码");
    private final String name;

    HealthCodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static HealthCodeType of(String value) {
        for (HealthCodeType type : values()) {
            if (type.name.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
