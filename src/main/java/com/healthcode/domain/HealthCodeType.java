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
    private final String type;

    HealthCodeType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static HealthCodeType of(String value) {
        for (HealthCodeType type : values()) {
            if (type.type.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
