package cn.lwf.framework.train.enums;


import cn.lwf.framework.train.common.ResponseCode;

/**
 * 通用系统层错误码
 *
 * @author zz@flyzz.net
 * @version $Id: CommonErrorCodeEnum.java v 0.1 2017/9/13 下午2:15 zz@flyzz.net Exp $$
 */
public enum CommonErrorCodeEnum implements ResponseCode {

    /**
     * 返回成功代码
     */
    SUCCESS(200, "成功"),

    /**
     * 默认的错误代码
     */
    SYSTEM_ERROR(100000, "系统异常，请稍后再试"),

    /**
     * 接口调用错误代码
     */
    INTERFACE_SYSTEM_ERROR(100001, "接口调用异常"),

    ;

    /**
     * 系统错误最小代码值
     */
    public final static int MIN_SYSTEM_ERROR_CODE = 100000;

    /**
     * 系统错误最大代码值
     */
    public final static int MAX_SYSTEM_ERROR_CODE = 199999;

    private int code;

    private String message;

    CommonErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过code获取枚举
     *
     * @param code
     * @return
     */
    public static CommonErrorCodeEnum getByCode(int code) {
        for (CommonErrorCodeEnum commonCodeEnum : values()) {
            if (commonCodeEnum.getCode() == code) {
                return commonCodeEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
