package org.goskyer.common;

public enum MsgCode {

    SUCCESSFUL(0, "操作成功"),
    FAIL(300, "操作失败"),
    BAD_PARAM(400, "参数异常"),
    LOGIN_REQURIED(405, "请先登录"),
    FORBIDDEN(403, "不允许访问"),
    NOT_FOUND(404, "资源找不到"),
    METHOD_NOT_ALLOW(401, "不支持的请求方法"),
    ILLEGAL_ERROR(406, "处理异常"),
    SYSTEM_ERROR(500,"系统异常");


    private int code;
    private String msg;

    private MsgCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
