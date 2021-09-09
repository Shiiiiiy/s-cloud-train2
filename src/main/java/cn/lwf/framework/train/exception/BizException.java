package cn.lwf.framework.train.exception;


import cn.lwf.framework.train.common.ResponseCode;
import cn.lwf.framework.train.enums.CommonErrorCodeEnum;

/**
 * 业务异常
 *
 * @author zz@flyzz.net
 * @version $Id: BizException.java v 0.1 2017/8/12 上午12:40 zz@flyzz.net Exp $$
 */
public class BizException extends RuntimeException {

    /**
     * 异常码
     */
    private int errorCode;

    /**
     * 异常消息
     */
    private String errorMessage;

    public BizException() {

    }

    public BizException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.errorCode = responseCode.getCode();
        this.errorMessage = responseCode.getMessage();
    }

    public BizException(ResponseCode responseCode, Throwable cause) {
        super(responseCode.getMessage(), cause);
        this.errorCode = responseCode.getCode();
        this.errorMessage = responseCode.getMessage();
    }

    /**
     * 是否是系统异常，错误码以1打头
     *
     * @return
     */
    public boolean isSystemError() {
        if (this.errorCode >= CommonErrorCodeEnum.MIN_SYSTEM_ERROR_CODE
                && this.errorCode <= CommonErrorCodeEnum.MAX_SYSTEM_ERROR_CODE) {
            return true;
        }
        return false;
    }

    /**
     * 默认业务异常
     *
     * @return
     */
    public static final BizException defaultBizException() {
        return new BizException(CommonErrorCodeEnum.SYSTEM_ERROR);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
