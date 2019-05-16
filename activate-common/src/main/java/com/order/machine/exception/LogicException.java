package com.order.machine.exception;

/**
 * @Description 业务逻辑异常类
 * @Author cjy
 * @Date 2019/2/14
 **/
public class LogicException extends RuntimeException {
    /**
     * 异常信息
     */
    private String errorMsg;
    /**
     * 错误码
     */
    private String code;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private LogicException(String code, String errorMsg) {
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    /**
     * 抛出逻辑异常
     *
     * @param code
     * @param errorMsg
     * @return
     */
    public static LogicException le(String code, String errorMsg) {
        return new LogicException(code, errorMsg);
    }
}