package cn.lwf.framework.train.enums;


import cn.lwf.framework.train.common.ResponseCode;

/**
 * 通用业务错误码
 *
 * @author zz@flyzz.net
 * @version $Id: CommonBizErrorCodeEnum.java v 0.1 2017/10/12 上午11:16 zz@flyzz.net Exp $$
 */
public enum CommonBizErrorCodeEnum implements ResponseCode {

    /**
     * 公共的业务错误代码从 200000 ~ 200500
     */

    // 200000 ~ 200029 参数相关的错误码
    ILLEGAL_ARGUMENT_ERROR(200000, "参数错误"),
    ILLEGAL_EMOJI_ARGUMENT_ERROR(200001, "emoji表情参数异常"),

    /**
     * 数据验证不合法,返回的默认错误码。一般该错误来自全局的VALIDATION异常捕获，严谨的业务可使用独立的错误码。
     */
    ILLEGAL_ARGUMENT_VALIDATION_ERROR(200002, "数据不合法"),

    // 200030 ~ 200049 接口请求相关的错误码
    INTERFACE_REQUEST_ERROR(200030, "接口数据请求失败"),

    // 访问的接口或者页面不存在
    PAGE_NOT_FOUND(200031, "页面不存在"),


    // 200050 ~ 200089 用户以及用户授权相关错误码
    USER_NOT_LOGIN(200050, "未登录"),
    USER_NOT_FOUND(200051, "用户不存在"),
    USER_PASSWORD_ERROR(200052, "密码错误"),

    // 用户可能被禁止
    USER_STATUS_ERROR(200053, "无效的用户"),

    // 用户微信，支付宝等第三方平台授权失败
    USER_AUTHORIZATION_FAILED(200054, "用户授权失败"),



    TOKEN_INVALID(200070, "无效的Token"),

    TOKEN_NOT_EMPTY(200071, "Token不能为空"),



    ;


    private int code;

    private String message;

    CommonBizErrorCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
