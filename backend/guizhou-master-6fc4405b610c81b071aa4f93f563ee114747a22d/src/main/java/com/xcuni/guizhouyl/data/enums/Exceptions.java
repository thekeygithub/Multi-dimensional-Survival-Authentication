package com.xcuni.guizhouyl.data.enums;


/**
 * 异常枚举值
 */
public enum Exceptions implements INameEnum {

    // Common
    None(200, "无异常"),
    UnknownException(1, "未知异常"),
    NotSupport(2, "不支持"),
    Closed(3, "已关闭"),

    InvalidRequest(400, "参数格式错误"),

    // System
    ParamEmpty(1001, "参数不能为空"),
    ParamInvalid(1002, "参数无效"),
    ParamFormatInvalid(1003, "格式错误"),

    OperationFail(1101, "操作失败"),
    OperationForbidden(1102, "禁止操作"),
    OperationDuplicate(1103, "操作重复"),

    ObjectNotFound(1201, "未找到指定的内容"),
    ObjectInvalid(1202, "指定的内容无效"),
    ObjectDisabled(1203, "指定的内容不可用"),
    ObjectExists(1204, "指定的内容已存在"),
    ;


    private int value;
    private String label;

    Exceptions(int value, String label) {
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


    public Exceptions setLabel(String label) {
        this.label = label;
        return this;
    }

    public boolean isValid() {
        return this.value == Exceptions.None.getValue();
    }

    /**
     * 通过数值获取枚举对象
     *
     * @param value
     * @return
     */
    public static Exceptions parse(int value) {
        Exceptions type = Exceptions.UnknownException;
        for (Exceptions t : Exceptions.values()) {
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
