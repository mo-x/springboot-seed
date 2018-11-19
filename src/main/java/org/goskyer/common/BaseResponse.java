package org.goskyer.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 返回模型
 * @author: mia.he
 * @date: 2017年11月17日 下午4:24:13
 * @version: V1.0
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 3288460493471365100L;

    private int msgCode;
    private String message;
    private T result;


    public static BaseResponse success() {
        BaseResponse result = new BaseResponse();
        result.setMessage(MsgCode.SUCCESSFUL.getMsg());
        result.setMsgCode(MsgCode.SUCCESSFUL.getCode());
        return result;
    }

    public static BaseResponse result(MsgCode msgCode) {
        BaseResponse responseResult = new BaseResponse();
        responseResult.msgCode = msgCode.getCode();
        responseResult.message = msgCode.getMsg();
        return responseResult;
    }

    public static BaseResponse result(MsgCode msgCode, String message) {
        BaseResponse responseResult = new BaseResponse();
        responseResult.msgCode = msgCode.getCode();
        responseResult.message = message == null ? msgCode.getMsg() : message;
        return responseResult;
    }

    public static BaseResponse result(int msgCode, String message) {
        BaseResponse responseResult = new BaseResponse();
        responseResult.msgCode = msgCode;
        responseResult.message = message;
        return responseResult;
    }

    public static <T> BaseResponse result(MsgCode msgCode, T result) {
        BaseResponse<T> responseResult = new BaseResponse<T>();
        responseResult.msgCode = msgCode.getCode();
        responseResult.message = msgCode.getMsg();
        responseResult.result = result;
        return responseResult;
    }

    public static <T> BaseResponse result(MsgCode msgCode, String message, T result) {
        BaseResponse responseResult = new BaseResponse();
        responseResult.msgCode = msgCode.getCode();
        responseResult.message = message == null ? msgCode.getMsg() : message;
        responseResult.result = result;
        return responseResult;
    }

    public static BaseResponse result(String message) {
        return result(MsgCode.SUCCESSFUL, message);
    }

    public static <T> BaseResponse result(T result) {
        BaseResponse responseResult = BaseResponse.result(MsgCode.SUCCESSFUL);
        responseResult.result = result;
        return responseResult;
    }
}
