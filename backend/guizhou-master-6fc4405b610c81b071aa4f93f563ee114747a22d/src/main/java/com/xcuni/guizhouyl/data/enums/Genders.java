package com.xcuni.guizhouyl.data.enums;

/**
 * 性别
 */
public enum Genders implements INameEnum {

    UNKNOWN(9, "未知"),
    MALE(1, "男"),
    FEMALE(2, "女"),
    SECRECY(99, "保密"),
    ;

    private int value;
    private String label;

    Genders(int value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 获取枚举变量对应的数值
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * 获取枚举变量对应的提示信息
     *
     * @return
     */
    public String getLabel() {
        return label;
    }

    public String getCode() {
        return String.valueOf(value);
    }

    /**
     * 通过数值获取枚举对象
     *
     * @param value
     * @return
     */
    public static Genders parse(int value) {
        Genders type = Genders.UNKNOWN;
        for (Genders t : Genders.values()) {
            if (value == t.getValue()) {
                type = t;
                break;
            }
        }
        return type;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }

}
