package com.shaomengjie.okhttp;

/**
 * Created by shaomengjie on 2017/3/15.
 */

public class AppException extends Exception {
    private AppException.ErrorType type;
    private int code;
    private String msg = "";

    public AppException(AppException.ErrorType type, String detailMessage) {
        super(detailMessage);
        this.type = type;
        this.msg = detailMessage;
    }

    public AppException(int code, String detailMessage) {
        super(detailMessage);
        this.type = AppException.ErrorType.SERVER;
        this.code = code;
        this.msg = detailMessage;
    }

    public AppException.ErrorType getType() {
        return this.type;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static enum ErrorType {
        IO,
        TIMEOUT,
        JSON,
        FILE_NOT_FIND,
        SERVER;

        private ErrorType() {
        }
    }
}
