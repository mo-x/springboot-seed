package org.goskyer.exception;


import org.goskyer.common.MsgCode;

/**
 * 自定义异常
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 3022481503667660306L;


    private int errorCode;
    private String errorMessage;
    private Object result;

    public CommonException(int errorCode, String message) {
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public CommonException(MsgCode msgCode) {
        this.errorCode = msgCode.getCode();
        this.errorMessage = msgCode.getMsg();
    }

    public CommonException(MsgCode msgCode, String message) {
        this.errorCode = msgCode.getCode();
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Object getResult() {
        return result;
    }

    @Override
    public String getMessage() {
        return null;
    }

    /**
     * 避免获取方法栈快照信息
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
